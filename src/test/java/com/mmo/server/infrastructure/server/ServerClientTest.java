package com.mmo.server.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.Packet;
import com.mmo.server.infrastructure.security.Decryptor;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.security.aes.AESDecryptor;
import com.mmo.server.infrastructure.security.aes.AESEncryptor;
import com.mmo.server.infrastructure.server.TestPacket.TestPacketConverter;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.client.ClientPacketReceiveSubscriber;
import com.mmo.server.infrastructure.server.client.ClientPacketSendSubscriber;
import com.mmo.server.infrastructure.server.packet.PacketGateway;

import lombok.Data;

public class ServerClientTest {

    @Test
    public void successfully() throws InterruptedException {
        TestPacketConverter converter = TestPacket.converter();

        PacketGateway.getInstance().bind(TestPacket.ALIAS, converter);

        String cipherKey = "Bar12345Bar12345";

        Encryptor encryptor = AESEncryptor.builder()
                .key(cipherKey)
                .build();

        Decryptor decryptor = AESDecryptor.builder()
                .key(cipherKey)
                .build();

        ClientWrapper clientConnected = new ClientWrapper();
        ClientWrapper clientDisconnected = new ClientWrapper();
        ClientPacketSubscriber serverClientSendSubscriber = new ClientPacketSubscriber();
        ClientPacketSubscriber serverClientReceiveSubscriber = new ClientPacketSubscriber();

        Server server = Server.builder()
                .port(5555)
                .encryptor(encryptor)
                .decryptor(decryptor)
                .connectSubscriber(clientConnected::setValue)
                .disconnectSubscriber(clientDisconnected::setValue)
                .sendSubscriber(serverClientSendSubscriber)
                .receiveSubscriber(serverClientReceiveSubscriber)
                .build();

        new Thread() {
            @Override
            public void run() {
                server.run();
            };
        }.start();

        Thread.sleep(1000);

        assertThat(server.isRunning(), equalTo(true));
        assertThat(clientConnected.value, nullValue());
        assertThat(clientDisconnected.value, nullValue());

        ClientPacketSubscriber clientSendSubscriber = new ClientPacketSubscriber();
        ClientPacketSubscriber clientReceiveSubscriber = new ClientPacketSubscriber();

        Client client = Client.clientBuilder()
                .host("localhost")
                .port(5555)
                .encryptor(encryptor)
                .decryptor(decryptor)
                .sendSubscriber(clientSendSubscriber)
                .receiveSubscriber(clientReceiveSubscriber)
                .buildClient();

        Thread.sleep(1000);

        UUID source = UUID.randomUUID();

        assertThat(clientConnected.value, notNullValue());
        assertThat(client.isConnected(), equalTo(true));

        TestPacket serverPacket = TestPacket.builder()
                .source(source)
                .property1("abc")
                .property2(3)
                .build();

        clientConnected.value.send(serverPacket);

        Thread.sleep(1000);

        // asserting client received server packet
        assertThat(clientReceiveSubscriber.packet, equalTo(serverPacket));

        TestPacket clientPacket = TestPacket.builder()
                .source(source)
                .property1("def")
                .property2(4)
                .build();

        client.send(clientPacket);

        Thread.sleep(1000);

        // asserting server received client packet
        assertThat(serverClientReceiveSubscriber.packet, equalTo(clientPacket));

        client.disconnect();

        Thread.sleep(1000);

        assertThat(client.isConnected(), equalTo(false));
        assertThat(clientDisconnected.value, notNullValue());

        server.stop();

        assertThat(server.isRunning(), equalTo(false));
    }

    private class ClientPacketSubscriber implements ClientPacketReceiveSubscriber, ClientPacketSendSubscriber {

        Packet packet;

        @Override
        public void onReceive(Client client, Packet packet) {
            this.packet = packet;
        }

        @Override
        public void onSend(Client client, Packet packet) {
            this.packet = packet;
        }
    }

    @Data
    private class ClientWrapper {
        Client value;
    }
}
