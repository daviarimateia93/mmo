package com.mmo.server.core.packet;

import com.mmo.server.core.map.Map;

@FunctionalInterface
public interface PacketHandler<T extends Packet> {

    void handle(Map map, T packet);
}
