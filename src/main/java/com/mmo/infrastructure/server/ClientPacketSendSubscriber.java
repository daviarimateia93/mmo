package com.mmo.infrastructure.server;

import com.mmo.core.packet.Packet;

@FunctionalInterface
public interface ClientPacketSendSubscriber {

    void onSend(Client client, Packet packet);
}
