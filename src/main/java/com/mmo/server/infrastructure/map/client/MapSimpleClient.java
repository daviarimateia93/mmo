package com.mmo.server.infrastructure.map.client;

import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.GoodByePacket;
import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.core.packet.PlayerAttackPacket;
import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.infrastructure.config.ConfigProvider;
import com.mmo.server.infrastructure.security.Decryptor;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.security.aes.AESDecryptor;
import com.mmo.server.infrastructure.security.aes.AESEncryptor;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.packet.PacketGateway;
import com.mmo.server.infrastructure.server.packet.converter.GoodByePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.HelloPacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.PlayerAttackPacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.PlayerMovePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.PlayerUpdatePacketConverter;

public class MapSimpleClient {

    private static final String CONFIG_MAP_SERVER_HOST = "map.server.host";
    private static final String CONFIG_MAP_SERVER_PORT = "map.server.port";
    private static final String CONFIG_MAP_SERVER_CIPHER_KEY = "map.server.cipher.key";

    private static final Logger logger = LoggerFactory.getLogger(MapSimpleClient.class);

    private final Client client;
    private final UUID source;
    private boolean exit;

    public MapSimpleClient() {
        java.util.Map.of(
                HelloPacket.ALIAS, new HelloPacketConverter(),
                GoodByePacket.ALIAS, new GoodByePacketConverter(),
                PlayerAttackPacket.ALIAS, new PlayerAttackPacketConverter(),
                GoodByePacket.ALIAS, new GoodByePacketConverter(),
                PlayerMovePacket.ALIAS, new PlayerMovePacketConverter(),
                PlayerUpdatePacket.ALIAS, new PlayerUpdatePacketConverter())
                .entrySet()
                .forEach(entry -> PacketGateway.getInstance().bind(entry.getKey(), entry.getValue()));

        source = UUID.randomUUID();
        client = createClient();

        client.send(HelloPacket.builder()
                .source(source)
                .build());

        runCommandLoop();

        client.disconnect();
    }

    private void runCommandLoop() {
        try (Scanner command = new Scanner(System.in)) {
            while (!exit) {
                switch (command.nextLine()) {
                case "exit":
                    exit = true;
                    break;

                default:
                    logger.info("default handler");
                }
            }
        }
    }

    private Client createClient() {
        Encryptor encryptor = AESEncryptor.builder()
                .key(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                .build();

        Decryptor decryptor = AESDecryptor.builder()
                .key(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                .build();

        Client client = Client.clientBuilder()
                .host(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_HOST))
                .port(ConfigProvider.getInstance().getInt(CONFIG_MAP_SERVER_PORT))
                .encryptor(encryptor)
                .decryptor(decryptor)
                .sendSubscriber(this::onSend)
                .receiveSubscriber(this::onReceive)
                .buildClient();

        return client;
    }

    private void onSend(Client client, Packet packet) {
        logger.info("Sent packet {} from client {}", packet, client);
    }

    private void onReceive(Client client, Packet packet) {
        logger.info("Received packet {} from client {}", packet, client);

        if (packet instanceof HelloPacket) {
            client.send(PlayerMovePacket.builder()
                    .source(source)
                    .target(Position.builder()
                            .x(200)
                            .z(100)
                            .build())
                    .build());
        }
    }

    public static void main(String... args) {
        new MapSimpleClient();
    }
}
