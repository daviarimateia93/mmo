package com.mmo.infrastructure.server;

@FunctionalInterface
public interface ClientPacketSendSubscriber {

    void onSend(Client client, Packet packet);
}
