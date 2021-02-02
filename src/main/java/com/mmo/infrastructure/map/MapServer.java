package com.mmo.infrastructure.map;

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
import com.mmo.infrastructure.map.packet.GoodByePacket;
import com.mmo.infrastructure.map.packet.HelloPacket;
import com.mmo.infrastructure.server.Client;
import com.mmo.infrastructure.server.Packet;
import com.mmo.infrastructure.server.Server;

public class MapServer {

    private static final UUID SERVER_SOURCE = UUID.fromString("39bb6712-db5c-4cae-9e67-143c3a97115d");
    private static final int SERVER_PORT = 5555;
    private static final String SERVER_CIPHER_KEY = "Bar12345Bar12345";
    private static final int HELLO_PACKET_WAITING_DELAY_IN_MINUTES = 5;
    private static final Logger logger = LoggerFactory.getLogger(MapServer.class);

    private final ConcurrentHashMap<Client, UUID> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Client> instanceIds = new ConcurrentHashMap<>();
    private final Map map;
    private final Server server;

    private MapServer() {
        logger.info("Loading map");

        map = loadMap();

        logger.info("Starting server");

        server = createServer();
        server.run();

        logger.info("Running game");

        Game.getInstance().run(map);
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
        Encryptor encryptor = Encryptor.builder()
                .key(SERVER_CIPHER_KEY)
                .build();

        Decryptor decryptor = Decryptor.builder()
                .key(SERVER_CIPHER_KEY)
                .build();

        return Server.builder()
                .port(SERVER_PORT)
                .encryptor(encryptor)
                .decryptor(decryptor)
                .onClientConnect(this::confirmClientConnected)
                .onClientDisconnect(this::removeClient)
                .sendSubscriber(this::onSend)
                .receiveSubscriber(this::onReceive)
                .build();
    }

    private void confirmClientConnected(Client client) {
        logger.info("Client has connected {}, waiting for HelloPacket", client);

        Executors.newSingleThreadScheduledExecutor()
                .schedule(() -> {
                    if (isConnected(client)) {
                        logger.info("Client sent HelloPacket");
                    } else {
                        disconnect(client);
                    }
                }, HELLO_PACKET_WAITING_DELAY_IN_MINUTES, TimeUnit.MINUTES);
    }

    private synchronized void addClient(Client client, UUID instanceId) {
        clients.put(client, instanceId);
        instanceIds.put(instanceId, client);
    }

    private synchronized void removeClient(Client client) {
        if (isConnected(client)) {
            // TODO
            GoodByePacket.builder().build(SERVER_SOURCE, new byte[0]);

            UUID instanceId = clients.remove(client);
            instanceIds.remove(instanceId);
            logger.info("Client has disconnected {}", client);
        }
    }

    private void onReceive(Client client, Packet packet) {
        logger.info("Received packet {} from client {}", packet, client);

        boolean connected = isConnected(client);

        if (!connected && packet instanceof HelloPacket) {
            addClient(client, packet.getSource());

            // TODO
            HelloPacket.builder().build(SERVER_SOURCE, new byte[0]);
            return;
        }

        if (!connected) {
            disconnect(client);
            return;
        }

        PacketHandlerDelegator.getInstance().delegate(this, packet);
    }

    private void onSend(Client client, Packet packet) {
        logger.info("Sent packet {} to client {}", packet, client);
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
        new MapServer();
    }
}
