package com.mmo.infrastructure.map.packet;

import com.mmo.infrastructure.map.MapServer;
import com.mmo.infrastructure.server.Packet;

public interface PacketHandler<T extends Packet> {

    void handle(MapServer server, T packet);
}
