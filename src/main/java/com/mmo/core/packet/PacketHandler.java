package com.mmo.core.packet;

import com.mmo.core.map.Map;

@FunctionalInterface
public interface PacketHandler<T extends Packet> {

    void handle(Map map, T packet);
}
