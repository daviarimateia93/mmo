package com.mmo.infrastructure.server;

@FunctionalInterface
public interface ClientConnectSubscriber {

    void onConnect(Client client);
}
