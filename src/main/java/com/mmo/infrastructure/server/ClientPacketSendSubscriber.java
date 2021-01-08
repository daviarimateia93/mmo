package com.mmo.infrastructure.server;

public interface ClientPacketSendSubscriber {

    void onSend(Client client, Packet packet);
}
