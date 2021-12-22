package com.mmo.infrastructure.map.server;

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.attribute.Attributes;
import com.mmo.core.game.Game;
import com.mmo.core.map.Map;
import com.mmo.core.map.MapEntity;
import com.mmo.core.map.Position;
import com.mmo.core.player.Player;
import com.mmo.core.stat.Stats;
import com.mmo.infrastructure.config.ConfigProvider;
import com.mmo.infrastructure.map.packet.AttackPacket;
import com.mmo.infrastructure.map.packet.GoodByePacket;
import com.mmo.infrastructure.map.packet.HelloPacket;
import com.mmo.infrastructure.map.packet.MovePacket;
import com.mmo.infrastructure.map.server.handler.AttackPacketHandler;
import com.mmo.infrastructure.map.server.handler.MovePacketHandler;
import com.mmo.infrastructure.map.server.handler.PacketHandlerDelegator;
import com.mmo.infrastructure.security.Decryptor;
import com.mmo.infrastructure.security.Encryptor;
import com.mmo.infrastructure.security.aes.AESDecryptor;
import com.mmo.infrastructure.security.aes.AESEncryptor;
import com.mmo.infrastructure.server.Client;
import com.mmo.infrastructure.server.Packet;
import com.mmo.infrastructure.server.PacketFactory;
import com.mmo.infrastructure.server.Server;

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
                .name("adventure_plains")
                .description("Located at the southern end, these plains were quiet and peaceful.")
                .nearbyRatio(10)
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
                .onClientConnect(this::confirmClientConnected)
                .onClientDisconnect(this::removeClient)
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
                        .x(random.ints(0, 1000).asLongStream().findFirst().getAsLong())
                        .y(random.ints(0, 1000).asLongStream().findFirst().getAsLong())
                        .z(random.ints(0, 1000).asLongStream().findFirst().getAsLong())
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

            sendNearby(GoodByePacket.builder()
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
                PacketHandlerDelegator.getInstance().delegate(this, packet);
            }
        } else {
            if (packet instanceof HelloPacket) {
                addClient(client, packet.getSource());

                logger.info("Client has sent HelloPacket, it is now connected");

                sendNearby(HelloPacket.builder()
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

    public void send(Packet packet, UUID target) {
        Player player = map.getEntity(target, Player.class);
        send(packet, Set.of(player));
    }

    public void sendNearby(Packet packet) {
        MapEntity entity = map.getEntity(packet.getSource());
        Set<Player> players = map.getNearbyEntities(entity, Player.class);
        send(packet, players);
    }

    private void send(Packet packet, Set<? extends MapEntity> targets) {
        targets.parallelStream()
                .map(MapEntity::getInstanceId)
                .map(instanceIds::get)
                .filter(this::isConnected)
                .forEach(client -> client.send(packet));
    }

    public static void main(String... args) {
        PacketFactory.getInstance()
                .bind(HelloPacket.ALIAS, HelloPacket.binaryBuilder())
                .bind(GoodByePacket.ALIAS, GoodByePacket.binaryBuilder())
                .bind(AttackPacket.ALIAS, AttackPacket.binaryBuilder())
                .bind(MovePacket.ALIAS, MovePacket.binaryBuilder());

        PacketHandlerDelegator.getInstance().bind(AttackPacket.class, new AttackPacketHandler());
        PacketHandlerDelegator.getInstance().bind(MovePacket.class, new MovePacketHandler());

        new MapServer();
    }
}
