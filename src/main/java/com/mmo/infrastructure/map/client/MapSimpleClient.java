package com.mmo.infrastructure.map.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.security.Decryptor;
import com.mmo.core.security.Encryptor;
import com.mmo.infrastructure.config.ConfigProvider;
import com.mmo.infrastructure.map.packet.AttackPacket;
import com.mmo.infrastructure.map.packet.GoodByePacket;
import com.mmo.infrastructure.map.packet.HelloPacket;
import com.mmo.infrastructure.map.packet.MovePacket;
import com.mmo.infrastructure.security.AESDecryptor;
import com.mmo.infrastructure.security.AESEncryptor;
import com.mmo.infrastructure.server.Client;
import com.mmo.infrastructure.server.Packet;
import com.mmo.infrastructure.server.PacketFactory;

public class MapSimpleClient {

    private static final String CONFIG_MAP_SERVER_HOST = "map.server.host";
    private static final String CONFIG_MAP_SERVER_PORT = "map.server.port";
    private static final String CONFIG_MAP_SERVER_CIPHER_KEY = "map.server.cipher.key";

    private static final Logger logger = LoggerFactory.getLogger(MapSimpleClient.class);

    private final Client client;

    public MapSimpleClient() {
        client = createClient();

        client.send(HelloPacket.builder()
                .source(UUID.randomUUID())
                .build());
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
                .port(ConfigProvider.getInstance().getInteger(CONFIG_MAP_SERVER_PORT))
                .encryptor(encryptor)
                .decryptor(decryptor)
                .sendSubscriber(this::onSend)
                .receiveSubscriber(this::onReceive)
                .clientBuild();

        return client;
    }

    private void onSend(Client client, Packet packet) {
        logger.info("Sent packet {} from client {}", packet, client);
    }

    private void onReceive(Client client, Packet packet) {
        logger.info("Received packet {} from client {}", packet, client);
    }

    public static void main(String... args) {
        PacketFactory.getInstance()
                .bind(HelloPacket.ALIAS, HelloPacket.binaryBuilder())
                .bind(GoodByePacket.ALIAS, GoodByePacket.binaryBuilder())
                .bind(AttackPacket.ALIAS, AttackPacket.binaryBuilder())
                .bind(MovePacket.ALIAS, MovePacket.binaryBuilder());

        new MapSimpleClient();
    }
}
