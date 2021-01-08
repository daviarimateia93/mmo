package com.mmo.infrastructure.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Server {

    private final Integer port;
    private final Set<Client> clients = new HashSet<>();
    private final Consumer<Client> onClientConnect;
    private final Consumer<Client> onClientDisconnect;
    private ServerSocket serverSocket;
    private boolean running;

    @Builder
    private Server(
            @NonNull Integer port,
            @NonNull Consumer<Client> onClientConnect,
            @NonNull Consumer<Client> onClientDisconnect) {

        this.port = port;
        this.onClientConnect = onClientConnect;
        this.onClientDisconnect = onClientDisconnect;
    }

    public Integer getPort() {
        return port;
    }

    public boolean isRunning() {
        return running;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            acceptClients();
        } catch (Exception exception) {
            throw new ServerStartException(exception, "Failed to start server socket");
        }
    }

    public void stop() {
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
                onClientConnect.accept(client);
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
                .onDisconnect(this::removeClient)
                .serverBuild();
    }

    private void removeClient(Client client) {
        clients.remove(client);
        onClientDisconnect.accept(client);
    }
}
