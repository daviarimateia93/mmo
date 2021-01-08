package com.mmo.infrastructure.server;

public interface ClientPacketReceiveSubscriber {

    void onReceive(Client client, Packet packet);
}
