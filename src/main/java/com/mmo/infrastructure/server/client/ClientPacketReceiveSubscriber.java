package com.mmo.infrastructure.server.client;

import com.mmo.core.packet.Packet;

@FunctionalInterface
public interface ClientPacketReceiveSubscriber {

    void onReceive(Client client, Packet packet);
}
