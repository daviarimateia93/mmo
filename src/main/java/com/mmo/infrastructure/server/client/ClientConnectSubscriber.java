package com.mmo.infrastructure.server.client;

@FunctionalInterface
public interface ClientConnectSubscriber {

    void onConnect(Client client);
}
