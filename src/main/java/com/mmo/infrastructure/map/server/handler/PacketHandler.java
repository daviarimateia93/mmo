package com.mmo.infrastructure.map.server.handler;

import com.mmo.core.packet.Packet;
import com.mmo.infrastructure.map.server.MapServer;

public interface PacketHandler<T extends Packet> {

    void handle(MapServer server, T packet);
}
