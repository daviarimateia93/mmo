package com.mmo.infrastructure.map;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.map.Position;
import com.mmo.core.packet.AttackPacket;
import com.mmo.core.packet.GoodByePacket;
import com.mmo.core.packet.HelloPacket;
import com.mmo.core.packet.MovePacket;
import com.mmo.core.packet.Packet;
import com.mmo.infrastructure.config.ConfigProvider;
import com.mmo.infrastructure.security.Decryptor;
import com.mmo.infrastructure.security.Encryptor;
import com.mmo.infrastructure.security.aes.AESDecryptor;
import com.mmo.infrastructure.security.aes.AESEncryptor;
import com.mmo.infrastructure.server.client.Client;
import com.mmo.infrastructure.server.packet.PacketGateway;
import com.mmo.infrastructure.server.packet.converter.AttackPacketConverter;
import com.mmo.infrastructure.server.packet.converter.GoodByePacketConverter;
import com.mmo.infrastructure.server.packet.converter.HelloPacketConverter;
import com.mmo.infrastructure.server.packet.converter.MovePacketConverter;

public class MapSimpleClient {

    private static final String CONFIG_MAP_SERVER_HOST = "map.server.host";
    private static final String CONFIG_MAP_SERVER_PORT = "map.server.port";
    private static final String CONFIG_MAP_SERVER_CIPHER_KEY = "map.server.cipher.key";

    private static final Logger logger = LoggerFactory.getLogger(MapSimpleClient.class);

    private final Client client;
    private final UUID source;

    public MapSimpleClient() {
        source = UUID.randomUUID();
        client = createClient();

        client.send(HelloPacket.builder()
                .source(source)
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

        if (packet instanceof HelloPacket) {
            client.send(MovePacket.builder()
                    .source(source)
                    .target(Position.builder()
                            .x(200L)
                            .y(100L)
                            .z(0L)
                            .build())
                    .build());
        }
    }

    public static void main(String... args) {
        PacketGateway.getInstance()
                .bind(HelloPacket.ALIAS, new HelloPacketConverter())
                .bind(GoodByePacket.ALIAS, new GoodByePacketConverter())
                .bind(AttackPacket.ALIAS, new AttackPacketConverter())
                .bind(MovePacket.ALIAS, new MovePacketConverter());

        new MapSimpleClient();
    }
}
