package com.mmo.server.infrastructure.server.client;

import com.mmo.server.core.packet.Packet;

@FunctionalInterface
public interface ClientPacketSendSubscriber {

    void onSend(Client client, Packet packet);
}
