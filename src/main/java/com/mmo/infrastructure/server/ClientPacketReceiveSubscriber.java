package com.mmo.infrastructure.server;

@FunctionalInterface
public interface ClientPacketReceiveSubscriber {

    void onReceive(Client client, Packet packet);
}
