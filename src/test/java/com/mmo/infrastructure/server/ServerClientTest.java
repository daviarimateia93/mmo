package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import lombok.Data;

public class ServerClientTest {

    @Test
    public void successfully() throws InterruptedException {
        PacketFactory.getInstance().register(TestPacket.ALIAS, TestPacket.builder());

        ClientWrapper clientConnected = new ClientWrapper();
        ClientWrapper clientDisconnected = new ClientWrapper();

        Server server = Server.builder()
                .port(5555)
                .onClientConnect(clientConnected::setValue)
                .onClientDisconnect(clientDisconnected::setValue)
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

        PacketWrapper onSend = new PacketWrapper();
        PacketWrapper onReceive = new PacketWrapper();

        Client client = Client.clientBuilder()
                .host("localhost")
                .port(5555)
                .onSend(onSend::setValue)
                .onReceive(onReceive::setValue)
                .clientBuild();

        Thread.sleep(1000);

        assertThat(clientConnected.value, notNullValue());
        assertThat(client.isConnected(), equalTo(true));

        TestPacket serverPacket = TestPacket.builder().build("abc", 3);
        clientConnected.value.send(serverPacket);

        Thread.sleep(1000);

        // asserting client received server packet
        assertThat(onReceive.value, equalTo(serverPacket));

        TestPacket clientPacket = TestPacket.builder().build("def", 4);
        client.send(clientPacket);

        Thread.sleep(1000);

        // TODO
        // asserting server received client packet
        // assertThat(clientConnected.value.value, equalTo(serverPacket));

        client.disconnect();

        Thread.sleep(1000);

        assertThat(client.isConnected(), equalTo(false));
        assertThat(clientDisconnected.value, notNullValue());

        server.stop();

        assertThat(server.isRunning(), equalTo(false));
    }

    @Data
    private class ClientWrapper {
        Client value;
    }

    @Data
    private class PacketWrapper {
        Packet value;
    }
}
