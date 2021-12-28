package com.mmo.server.infrastructure.server.client;

@FunctionalInterface
public interface ClientConnectSubscriber {

    void onConnect(Client client);
}
