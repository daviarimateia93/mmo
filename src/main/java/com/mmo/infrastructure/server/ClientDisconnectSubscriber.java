package com.mmo.infrastructure.server;

@FunctionalInterface
public interface ClientDisconnectSubscriber {

    void onDisconnect(Client client);
}
