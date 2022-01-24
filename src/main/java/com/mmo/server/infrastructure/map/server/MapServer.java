package com.mmo.server.infrastructure.map.server;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.game.Game;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.MapEntity;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.map.Terrain;
import com.mmo.server.core.math.Rectangle;
import com.mmo.server.core.math.Vertex;
import com.mmo.server.core.packet.AnimateAttackPacket;
import com.mmo.server.core.packet.AnimateDiePacket;
import com.mmo.server.core.packet.AnimateMovePacket;
import com.mmo.server.core.packet.GoodByePacket;
import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.core.packet.NetworkPacket;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.core.packet.PacketHandlerDelegator;
import com.mmo.server.core.packet.PersistencePacket;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.config.ConfigProvider;
import com.mmo.server.infrastructure.packet.AnimateAttackPacketHandler;
import com.mmo.server.infrastructure.packet.AnimateMovePacketHandler;
import com.mmo.server.infrastructure.security.Decryptor;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.security.aes.AESDecryptor;
import com.mmo.server.infrastructure.security.aes.AESEncryptor;
import com.mmo.server.infrastructure.server.Server;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.packet.PacketGateway;
import com.mmo.server.infrastructure.server.packet.converter.AnimateAttackPacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.AnimateDiePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.AnimateMovePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.GoodByePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.HelloPacketConverter;

public class MapServer {

    private static final String CONFIG_MAP_SERVER_HELLO_PACKET_WAITING_DELAY_IN_MINUTES = "map.server.hello.packet.waiting.delay.in.minutes";
    private static final String CONFIG_MAP_SERVER_PORT = "map.server.port";
    private static final String CONFIG_MAP_SERVER_CIPHER_KEY = "map.server.cipher.key";

    private static final Logger logger = LoggerFactory.getLogger(MapServer.class);

    private final ConcurrentHashMap<Client, UUID> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Client> instanceIds = new ConcurrentHashMap<>();
    private final Map map;
    private final Server server;

    private MapServer() {
        logger.info("Loading map");

        map = loadMap();

        logger.info("Running game");

        runGame();

        logger.info("Starting server");

        server = createServer();
        server.run();
    }

    private void runGame() {
        Executors.newSingleThreadScheduledExecutor()
                .execute(() -> Game.getInstance().run(map));
    }

    public Map getMap() {
        return map;
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

    private Server createServer() {
        Encryptor encryptor = AESEncryptor.builder()
                .key(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                .build();

        Decryptor decryptor = AESDecryptor.builder()
                .key(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                .build();

        return Server.builder()
                .port(ConfigProvider.getInstance().getInteger(CONFIG_MAP_SERVER_PORT))
                .encryptor(encryptor)
                .decryptor(decryptor)
                .connectSubscriber(this::confirmClientConnected)
                .disconnectSubscriber(this::removeClient)
                .sendSubscriber(this::onSend)
                .receiveSubscriber(this::onReceive)
                .build();
    }

    private void confirmClientConnected(Client client) {
        logger.info("Client bound {}, waiting for HelloPacket", client);

        Executors.newSingleThreadScheduledExecutor()
                .schedule(() -> {
                    if (isConnected(client)) {
                        logger.info("Client sent HelloPacket");
                    } else {
                        logger.info("Client did not send HelloPacket, it will disconnect", client);

                        client.disconnect();
                    }
                },
                        ConfigProvider.getInstance().getLong(CONFIG_MAP_SERVER_HELLO_PACKET_WAITING_DELAY_IN_MINUTES),
                        TimeUnit.MINUTES);
    }

    private synchronized void addClient(Client client, UUID instanceId) {
        Random random = new Random();

        Player player = Player.builder()
                .instanceId(instanceId)
                .name("PlayerName-" + UUID.randomUUID())
                .position(Position.builder()
                        .x(random.ints(0, 1000).findFirst().getAsInt())
                        .z(random.ints(0, 1000).findFirst().getAsInt())
                        .build())
                .stats(Stats.builder()
                        .strength(10)
                        .dexterity(10)
                        .intelligence(10)
                        .concentration(10)
                        .sense(10)
                        .charm(10)
                        .build())
                .attributes(Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build())
                .build();

        clients.put(client, instanceId);
        instanceIds.put(instanceId, client);
        map.addEntity(player);
    }

    private synchronized void removeClient(Client client) {
        if (isConnected(client)) {
            UUID instanceId = clients.remove(client);
            instanceIds.remove(instanceId);
            map.removeEntity(instanceId);

            logger.info("Client has disconnected {}", client);

            send(GoodByePacket.builder()
                    .source(instanceId)
                    .build());
        }
    }

    private void onSend(Client client, Packet packet) {
        logger.info("Sent packet {} to client {}", packet, client);
    }

    private void onReceive(Client client, Packet packet) {
        logger.info("Received packet {} from client {}", packet, client);

        boolean connected = isConnected(client);

        if (connected) {
            if (packet instanceof GoodByePacket) {
                logger.info("Client has sent GoodByePacket, it will disconnect");

                client.disconnect();
            } else {
                PacketHandlerDelegator.getInstance().delegate(map, packet);
            }
        } else {
            if (packet instanceof HelloPacket) {
                addClient(client, packet.getSource());

                logger.info("Client has sent HelloPacket, it is now connected");

                send(HelloPacket.builder()
                        .source(packet.getSource())
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
            PacketHandlerDelegator.getInstance().delegate(map, packet);
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
                .map(MapEntity::getInstanceId)
                .map(instanceIds::get)
                .filter(this::isConnected)
                .forEach(client -> client.send(packet));
    }

    public static void main(String... args) {
        PacketGateway.getInstance()
                .bind(HelloPacket.ALIAS, new HelloPacketConverter())
                .bind(GoodByePacket.ALIAS, new GoodByePacketConverter())
                .bind(AnimateAttackPacket.ALIAS, new AnimateAttackPacketConverter())
                .bind(AnimateMovePacket.ALIAS, new AnimateMovePacketConverter())
                .bind(AnimateDiePacket.ALIAS, new AnimateDiePacketConverter());

        PacketHandlerDelegator.getInstance()
                .bind(AnimateAttackPacket.class, new AnimateAttackPacketHandler())
                .bind(AnimateMovePacket.class, new AnimateMovePacketHandler());

        new MapServer();
    }
}
