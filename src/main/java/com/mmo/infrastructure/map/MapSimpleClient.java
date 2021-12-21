package com.mmo.infrastructure.map;

import java.util.UUID;

import com.mmo.core.security.Decryptor;
import com.mmo.core.security.Encryptor;
import com.mmo.infrastructure.map.packet.HelloPacket;
import com.mmo.infrastructure.security.AESDecryptor;
import com.mmo.infrastructure.security.AESEncryptor;
import com.mmo.infrastructure.server.Client;
import com.mmo.infrastructure.server.Packet;

public class MapSimpleClient {

    private final Client client;

    public MapSimpleClient() {
        client = createClient();

        client.send(HelloPacket.builder()
                .source(UUID.randomUUID())
                .build());
    }

    private Client createClient() {
        Encryptor encryptor = AESEncryptor.builder()
                .key(MapServer.SERVER_CIPHER_KEY)
                .build();

        Decryptor decryptor = AESDecryptor.builder()
                .key(MapServer.SERVER_CIPHER_KEY)
                .build();

        Client client = Client.clientBuilder()
                .host(MapServer.SERVER_HOST)
                .port(MapServer.SERVER_PORT)
                .encryptor(encryptor)
                .decryptor(decryptor)
                .sendSubscriber(this::onSend)
                .receiveSubscriber(this::onReceive)
                .clientBuild();

        return client;
    }

    private void onSend(Client client, Packet packet) {

    }

    private void onReceive(Client client, Packet packet) {

    }

    public static void main(String... args) {
        MapServer.registerPackets();
        
        new MapSimpleClient();
    }
}
