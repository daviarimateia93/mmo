package com.mmo.infrastructure.map;

import com.mmo.core.map.Map;
import com.mmo.infrastructure.server.Packet;

public interface PacketHandler<T extends Packet> {

    void handle(Map map, T packet);
}
