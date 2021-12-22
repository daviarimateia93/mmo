package com.mmo.infrastructure.server.client;

@FunctionalInterface
public interface ClientDisconnectSubscriber {

    void onDisconnect(Client client);
}
