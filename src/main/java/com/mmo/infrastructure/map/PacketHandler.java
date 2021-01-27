package com.mmo.infrastructure.map;

import com.mmo.infrastructure.server.Packet;

public interface PacketHandler<T extends Packet> {

    void handle(MapServer server, T packet);
}
