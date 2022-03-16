package com.mmo.server.infrastructure.map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.infrastructure.config.ConfigProvider;
import com.mmo.server.infrastructure.map.server.MapServer;
import com.mmo.server.infrastructure.security.aes.AESDecryptor;
import com.mmo.server.infrastructure.security.aes.AESEncryptor;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.client.ClientPacketReceiveSubscriber;
import com.mmo.server.infrastructure.server.client.ClientPacketSendSubscriber;

public class MapServerTest {

    private static final String CONFIG_MAP_SERVER_HOST = "map.server.host";
    private static final String CONFIG_MAP_SERVER_PORT = "map.server.port";
    private static final String CONFIG_MAP_SERVER_CIPHER_KEY = "map.server.cipher.key";
    private static final String CONFIG_PLAYER_ID_DEFAULT = "admin[1].player.id";
    private static final String CONFIG_USER_NAME_DEFAULT = "admin[1].user.name";
    private static final String CONFIG_USER_PASSWORD_DEFAULT = "admin[1].user.password";

    private static MapServer server;
    private static ConfigProvider configProvider;

    private Client client;
    private Packet sentPacket;
    private Packet receivedPacket;

    @BeforeAll
    public static void setup() {
        server = new MapServer();
        configProvider = ConfigProvider.getInstance();
    }

    @Test
    public void startAndStop() throws InterruptedException {
        server.start();

        Thread.sleep(2 * 1000); // wait for server up

        client = newClient(
                (client, packet) -> {
                    assertThat(client, equalTo(this.client));
                    sentPacket = packet;
                },
                (client, packet) -> {
                    assertThat(client, equalTo(this.client));
                    receivedPacket = packet;
                });

        client.send(HelloPacket.builder()
                .source(configProvider.getUUID(CONFIG_PLAYER_ID_DEFAULT))
                .userName(configProvider.getString(CONFIG_USER_NAME_DEFAULT))
                .userPassword(configProvider.getString(CONFIG_USER_PASSWORD_DEFAULT))
                .build());

        Thread.sleep(1 * 1000); // wait for server answer

        assertThat(sentPacket, not(nullValue()));
        assertThat(receivedPacket, nullValue());

        server.stop();
    }

    private Client newClient(
            ClientPacketSendSubscriber sendSubscriber,
            ClientPacketReceiveSubscriber receiveSubscriber) {

        return Client.clientBuilder()
                .host(configProvider.getString(CONFIG_MAP_SERVER_HOST))
                .port(configProvider.getInt(CONFIG_MAP_SERVER_PORT))
                .encryptor(AESEncryptor.builder()
                        .key(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                        .build())
                .decryptor(AESDecryptor.builder()
                        .key(ConfigProvider.getInstance().getString(CONFIG_MAP_SERVER_CIPHER_KEY))
                        .build())
                .sendSubscriber(sendSubscriber)
                .receiveSubscriber(receiveSubscriber)
                .buildClient();
    }
}
