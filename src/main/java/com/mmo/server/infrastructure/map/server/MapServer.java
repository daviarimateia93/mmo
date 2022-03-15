package com.mmo.server.infrastructure.map.server;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.server.core.game.Game;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.MapEntity;
import com.mmo.server.core.map.Terrain;
import com.mmo.server.core.math.Rectangle;
import com.mmo.server.core.math.Vertex;
import com.mmo.server.core.packet.GoodByePacket;
import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.core.packet.NetworkPacket;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.core.packet.PacketHandlerDelegator;
import com.mmo.server.core.packet.PersistencePacket;
import com.mmo.server.core.packet.PlayerAttackPacket;
import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.core.packet.PlayerPersistPacket;
import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.core.user.UserRepository;
import com.mmo.server.infrastructure.config.ConfigProvider;
import com.mmo.server.infrastructure.packet.PlayerAttackPacketHandler;
import com.mmo.server.infrastructure.packet.PlayerMovePacketHandler;
import com.mmo.server.infrastructure.packet.PlayerPersistPacketHandler;
import com.mmo.server.infrastructure.player.MongoPlayerRepository;
import com.mmo.server.infrastructure.security.Authenticator;
import com.mmo.server.infrastructure.security.Decryptor;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.security.aes.AESDecryptor;
import com.mmo.server.infrastructure.security.aes.AESEncryptor;
import com.mmo.server.infrastructure.server.Server;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.packet.PacketGateway;
import com.mmo.server.infrastructure.server.packet.converter.GoodByePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.HelloPacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.PlayerAttackPacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.PlayerMovePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.PlayerUpdatePacketConverter;
import com.mmo.server.infrastructure.setup.AdminSetupper;
import com.mmo.server.infrastructure.user.MongoUserRepository;

public final class MapServer {

    private static final String CONFIG_MAP_SERVER_HELLO_PACKET_WAITING_DELAY_IN_MINUTES = "map.server.hello.packet.waiting.delay.in.minutes";
    private static final String CONFIG_MAP_SERVER_PORT = "map.server.port";
    private static final String CONFIG_MAP_SERVER_CIPHER_KEY = "map.server.cipher.key";

    private static final Logger logger = LoggerFactory.getLogger(MapServer.class);

    private final ConcurrentHashMap<Client, UUID> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Client> instanceIds = new ConcurrentHashMap<>();
    private final ConfigProvider configProvider;
    private final Map map;
    private final Authenticator authenticator;
    private final Game game;
    private final Server server;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final ScheduledExecutorService clientConfirmPool = Executors.newSingleThreadScheduledExecutor();

    public MapServer() {
        configProvider = ConfigProvider.getInstance();
        userRepository = new MongoUserRepository();
        playerRepository = new MongoPlayerRepository();

        logger.info("Initializing admin setup");

        setup();

        logger.info("Binding packet converters and handlers");

        bindConverters();

        bindHandlers();

        logger.info("Loading map");

        map = loadMap();

        logger.info("Creating authenticator");

        authenticator = newAuthenticator();

        game = Game.getInstance();

        server = newServer();
    }

    public void start() {
        logger.info("Running game");
        Executors.newSingleThreadScheduledExecutor()
                .execute(() -> game.run(map));

        logger.info("Starting server");
        Executors.newSingleThreadScheduledExecutor()
                .execute(() -> server.run());
    }

    public void stop() {
        game.stop();
        server.stop();
    }

    private void setup() {
        AdminSetupper.builder()
                .userRepository(userRepository)
                .playerRepository(playerRepository)
                .build()
                .setup();
    }

    private void bindConverters() {
        PacketGateway.getInstance()
                .bind(HelloPacket.ALIAS, new HelloPacketConverter())
                .bind(GoodByePacket.ALIAS, new GoodByePacketConverter())
                .bind(PlayerAttackPacket.ALIAS, new PlayerAttackPacketConverter())
                .bind(PlayerMovePacket.ALIAS, new PlayerMovePacketConverter())
                .bind(PlayerUpdatePacket.ALIAS, new PlayerUpdatePacketConverter());

    }

    private void bindHandlers() {
        PacketHandlerDelegator.getInstance()
                .bind(PlayerAttackPacket.class, new PlayerAttackPacketHandler())
                .bind(PlayerMovePacket.class, new PlayerMovePacketHandler())
                .bind(PlayerPersistPacket.class, new PlayerPersistPacketHandler(playerRepository));
    }

    private Map loadMap() {
        return Map.builder()
                .id(UUID.randomUUID())
                .name("adventure_plains")
                .description("Located at the southern end, these plains were quiet and peaceful.")
                .nearbyRatio(10)
                .packetSubscribers(Set.of(this::persist, this::send))
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .forbiddenAreas(List.of(
                                Rectangle.builder()
                                        .bottomLeftVertex(new Vertex(10, 11))
                                        .bottomRightVertex(new Vertex(20, 11))
                                        .topLeftVertex(new Vertex(10, 21))
                                        .topRightVertex(new Vertex(20, 21))
                                        .build(),
                                Rectangle.builder()
                                        .bottomLeftVertex(new Vertex(110, 111))
                                        .bottomRightVertex(new Vertex(120, 111))
                                        .topLeftVertex(new Vertex(110, 121))
                                        .topRightVertex(new Vertex(120, 121))
                                        .build()))
                        .build())
                .build();
    }

    private Server newServer() {
        Encryptor encryptor = AESEncryptor.builder()
                .key(configProvider.getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                .build();

        Decryptor decryptor = AESDecryptor.builder()
                .key(configProvider.getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                .build();

        return Server.builder()
                .port(configProvider.getInt(CONFIG_MAP_SERVER_PORT))
                .encryptor(encryptor)
                .decryptor(decryptor)
                .connectSubscriber(this::confirmClientConnected)
                .disconnectSubscriber(this::removeClient)
                .sendSubscriber(this::onSend)
                .receiveSubscriber(this::onReceive)
                .build();
    }

    private Authenticator newAuthenticator() {
        return Authenticator.builder()
                .userRepository(userRepository)
                .playerRepository(playerRepository)
                .build();
    }

    private void confirmClientConnected(Client client) {
        logger.info("Client bound {}, waiting for HelloPacket", client);

        clientConfirmPool.schedule(() -> {
            if (isConnected(client)) {
                logger.info("Client sent HelloPacket");
            } else {
                logger.info("Client did not send HelloPacket, it will disconnect", client);

                client.disconnect();
            }
        },
                configProvider.getLong(CONFIG_MAP_SERVER_HELLO_PACKET_WAITING_DELAY_IN_MINUTES),
                TimeUnit.MINUTES);
    }

    private boolean validateClientAndInstanceId(Client client, UUID instanceId) {
        return Optional.ofNullable(clients.get(client))
                .filter(instanceId::equals)
                .isPresent();
    }

    private synchronized void addClient(Client client, UUID instanceId) {
        Player player = playerRepository.find(instanceId).orElseThrow();

        clients.put(client, instanceId);
        instanceIds.put(instanceId, client);
        map.addEntity(player);
    }

    private synchronized void removeClient(Client client) {
        if (isConnected(client)) {
            UUID instanceId = clients.remove(client);
            instanceIds.remove(instanceId);

            logger.info("Client has disconnected {}", client);

            send(GoodByePacket.builder()
                    .source(instanceId)
                    .build());

            map.removeEntity(instanceId);
        }
    }

    private void onSend(Client client, Packet packet) {
        logger.info("Sent packet {} to client {}", packet, client);
    }

    private void onReceive(Client client, Packet packet) {
        logger.info("Received packet {} from client {}", packet, client);

        boolean connected = isConnected(client);

        if (connected) {
            if (!validateClientAndInstanceId(client, packet.getSource())) {
                logger.info("InstanceId does not belongs to this client");
                client.disconnect();
            }

            if (packet instanceof GoodByePacket) {
                logger.info("Client has sent GoodByePacket, it will disconnect");

                client.disconnect();
            } else {
                PacketHandlerDelegator.getInstance().delegate(packet);
            }
        } else {
            if (packet instanceof HelloPacket) {
                HelloPacket helloPacket = (HelloPacket) packet;

                boolean authenticated = authenticator.authenticate(
                        helloPacket.getUserName(),
                        helloPacket.getUserPassword(),
                        helloPacket.getSource());

                if (!authenticated) {
                    logger.info("Client has not authenticated");

                    client.disconnect();
                }

                addClient(client, packet.getSource());

                logger.info("Client has sent HelloPacket, it is now connected");

                send(PlayerUpdatePacket.builder()
                        .source(helloPacket.getSource())
                        .player(map.getEntity(helloPacket.getSource(), Player.class))
                        .build());
            } else {
                logger.info("Client is not connected, forcing disconnect");

                client.disconnect();
            }
        }
    }

    private boolean isConnected(Client client) {
        return clients.containsKey(client);
    }

    private void persist(Packet packet, Optional<UUID> target) {
        if (packet instanceof PersistencePacket) {
            PacketHandlerDelegator.getInstance().delegate(packet);
        }
    }

    private void send(Packet packet, Optional<UUID> target) {
        if (packet instanceof NetworkPacket) {
            NetworkPacket networkPacket = (NetworkPacket) packet;
            target.ifPresentOrElse(value -> send(networkPacket, value), () -> send(networkPacket));
        }
    }

    private void send(NetworkPacket packet, UUID target) {
        Player player = map.getEntity(target, Player.class);
        send(packet, Set.of(player));
    }

    private void send(NetworkPacket packet) {
        MapEntity entity = map.getEntity(packet.getSource());
        Set<Player> players = map.getNearbyEntities(entity, Player.class);
        send(packet, players);
    }

    private void send(NetworkPacket packet, Set<? extends MapEntity> targets) {
        targets.parallelStream()
                .filter(target -> !target.getInstanceId().equals(packet.getSource()))
                .map(MapEntity::getInstanceId)
                .map(instanceIds::get)
                .filter(this::isConnected)
                .forEach(client -> client.send(packet));
    }

    public static void main(String... args) {
        new MapServer().start();
    }
}
