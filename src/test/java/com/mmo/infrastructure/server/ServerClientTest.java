package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import lombok.Data;

public class ServerClientTest {

    @Test
    public void successfully() throws InterruptedException {
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

        // TODO send packet server, assert client receive
        // TODO send packet client, assert client server receive

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
