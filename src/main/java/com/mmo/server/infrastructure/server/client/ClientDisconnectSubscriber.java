package com.mmo.server.infrastructure.server.client;

@FunctionalInterface
public interface ClientDisconnectSubscriber {

    void onDisconnect(Client client);
}
