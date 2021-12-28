package com.mmo.server.infrastructure.map.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.AnimateAttackPacket;
import com.mmo.server.core.packet.AnimateDiePacket;
import com.mmo.server.core.packet.AnimateMovePacket;
import com.mmo.server.core.packet.GoodByePacket;
import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.infrastructure.config.ConfigProvider;
import com.mmo.server.infrastructure.security.Decryptor;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.security.aes.AESDecryptor;
import com.mmo.server.infrastructure.security.aes.AESEncryptor;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.packet.PacketGateway;
import com.mmo.server.infrastructure.server.packet.converter.AnimateAttackPacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.AnimateDiePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.AnimateMovePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.GoodByePacketConverter;
import com.mmo.server.infrastructure.server.packet.converter.HelloPacketConverter;

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
            client.send(AnimateMovePacket.builder()
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
                .bind(AnimateAttackPacket.ALIAS, new AnimateAttackPacketConverter())
                .bind(AnimateMovePacket.ALIAS, new AnimateMovePacketConverter())
                .bind(AnimateDiePacket.ALIAS, new AnimateDiePacketConverter());

        new MapSimpleClient();
    }
}
