package com.mmo.infrastructure.map.server.handler;

import com.mmo.infrastructure.map.server.MapServer;
import com.mmo.infrastructure.server.Packet;

public interface PacketHandler<T extends Packet> {

    void handle(MapServer server, T packet);
}
