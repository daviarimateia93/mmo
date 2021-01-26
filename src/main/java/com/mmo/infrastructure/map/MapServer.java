package com.mmo.infrastructure.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.game.Game;
import com.mmo.core.map.Map;
import com.mmo.core.security.Decryptor;
import com.mmo.core.security.Encryptor;
import com.mmo.infrastructure.server.Client;
import com.mmo.infrastructure.server.ClientPacketReceiveSubscriber;
import com.mmo.infrastructure.server.ClientPacketSendSubscriber;
import com.mmo.infrastructure.server.Packet;
import com.mmo.infrastructure.server.Server;

public class MapServer implements ClientPacketSendSubscriber, ClientPacketReceiveSubscriber {

    private static Logger logger = LoggerFactory.getLogger(MapServer.class);

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

    private Map loadMap() {
        return Map.builder()
                .name("adventure_plains")
                .description("Located at the southern end, these plains were quiet and peaceful.")
                .nearbyRatio(10)
                .build();
    }

    private Server createServer() {
        String cipherKey = "Bar12345Bar12345";

        Encryptor encryptor = Encryptor.builder()
                .key(cipherKey)
                .build();

        Decryptor decryptor = Decryptor.builder()
                .key(cipherKey)
                .build();

        return Server.builder()
                .port(5555)
                .encryptor(encryptor)
                .decryptor(decryptor)
                .onClientConnect(this::addClient)
                .onClientDisconnect(this::removeClient)
                .sendSubscriber(this)
                .receiveSubscriber(this)
                .build();
    }

    private void addClient(Client client) {

    }

    private void removeClient(Client client) {

    }

    @Override
    public void onReceive(Client client, Packet packet) {

    }

    @Override
    public void onSend(Client client, Packet packet) {

    }

    public static void main(String... args) {
        new MapServer();
    }
}
