package com.mmo.server.infrastructure.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import com.mmo.server.infrastructure.security.Decryptor;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.server.client.Client;
import com.mmo.server.infrastructure.server.client.ClientConnectSubscriber;
import com.mmo.server.infrastructure.server.client.ClientDisconnectSubscriber;
import com.mmo.server.infrastructure.server.client.ClientPacketReceiveSubscriber;
import com.mmo.server.infrastructure.server.client.ClientPacketSendSubscriber;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Server {

    private final Integer port;
    private final Encryptor encryptor;
    private final Decryptor decryptor;
    private final Set<Client> clients = new HashSet<>();
    private final ClientConnectSubscriber connectSubscriber;
    private final ClientDisconnectSubscriber disconnectSubscriber;
    private final ClientPacketSendSubscriber sendSubscriber;
    private final ClientPacketReceiveSubscriber receiveSubscriber;
    private ServerSocket serverSocket;
    private boolean running;

    @Builder
    private Server(
            @NonNull Integer port,
            @NonNull Encryptor encryptor,
            @NonNull Decryptor decryptor,
            @NonNull ClientConnectSubscriber connectSubscriber,
            @NonNull ClientDisconnectSubscriber disconnectSubscriber,
            @NonNull ClientPacketSendSubscriber sendSubscriber,
            @NonNull ClientPacketReceiveSubscriber receiveSubscriber) {

        this.port = port;
        this.encryptor = encryptor;
        this.decryptor = decryptor;
        this.connectSubscriber = connectSubscriber;
        this.disconnectSubscriber = disconnectSubscriber;
        this.sendSubscriber = sendSubscriber;
        this.receiveSubscriber = receiveSubscriber;
    }

    public Integer getPort() {
        return port;
    }

    public boolean isRunning() {
        return running;
    }

    public void run() throws ServerStartException, ServerListeningException {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            acceptClients();
        } catch (Exception exception) {
            throw new ServerStartException(exception, "Failed to start server socket");
        }
    }

    public void stop() throws ServerStopException {
        try {
            serverSocket.close();
        } catch (Exception exception) {
            throw new ServerStopException(exception, "Failed to stop server socket");
        } finally {
            running = false;
            clients.forEach(Client::disconnect);
        }
    }

    private void acceptClients() {
        Socket socket;

        try {
            while ((socket = serverSocket.accept()) != null) {
                Client client = newClient(socket);
                clients.add(client);
                connectSubscriber.onConnect(client);
            }
        } catch (Exception exception) {
            throw new ServerListeningException(exception, "Server stoped listening");
        } finally {
            stop();
        }
    }

    private Client newClient(Socket socket) {
        return Client.serverBuilder()
                .socket(socket)
                .encryptor(encryptor)
                .decryptor(decryptor)
                .disconnectSubscriber(this::removeClient)
                .sendSubscriber(sendSubscriber)
                .receiveSubscriber(receiveSubscriber)
                .buildServer();
    }

    private void removeClient(Client client) {
        clients.remove(client);
        disconnectSubscriber.onDisconnect(client);
    }
}
