package com.mmo.infrastructure.map.server;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.game.Game;
import com.mmo.core.map.Map;
import com.mmo.core.map.MapEntity;
import com.mmo.core.player.Player;
import com.mmo.core.security.Decryptor;
import com.mmo.core.security.Encryptor;
import com.mmo.infrastructure.config.ConfigProvider;
import com.mmo.infrastructure.map.packet.AttackPacket;
import com.mmo.infrastructure.map.packet.GoodByePacket;
import com.mmo.infrastructure.map.packet.HelloPacket;
import com.mmo.infrastructure.map.packet.MovePacket;
import com.mmo.infrastructure.map.server.handler.PacketHandlerDelegator;
import com.mmo.infrastructure.security.AESDecryptor;
import com.mmo.infrastructure.security.AESEncryptor;
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
                        disconnect(client);
                    }
                },
                        ConfigProvider.getInstance().getLong(CONFIG_MAP_SERVER_HELLO_PACKET_WAITING_DELAY_IN_MINUTES),
                        TimeUnit.MINUTES);
    }

    private synchronized void addClient(Client client, UUID instanceId) {
        clients.put(client, instanceId);
        instanceIds.put(instanceId, client);
    }

    private synchronized void removeClient(Client client) {
        if (isConnected(client)) {
            UUID instanceId = clients.remove(client);
            instanceIds.remove(instanceId);

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

        if (!connected && packet instanceof HelloPacket) {
            addClient(client, packet.getSource());

            logger.info("Client has sent HelloPacket, it is now connected");

            sendNearby(HelloPacket.builder()
                    .source(packet.getSource())
                    .build());
            return;
        }

        if (!connected) {
            logger.info("Client is not connected, forcing disconnect");

            disconnect(client);
            return;
        }

        PacketHandlerDelegator.getInstance().delegate(this, packet);
    }

    private boolean isConnected(Client client) {
        return clients.containsKey(client);
    }

    private void disconnect(Client client) {
        logger.info("Client did not send HelloPacket, it will disconnect", client);
        client.disconnect();
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

        new MapServer();
    }
}
