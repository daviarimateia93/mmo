package com.mmo.server.infrastructure.server.client;

import com.mmo.server.core.packet.Packet;

@FunctionalInterface
public interface ClientPacketReceiveSubscriber {

    void onReceive(Client client, Packet packet);
}
