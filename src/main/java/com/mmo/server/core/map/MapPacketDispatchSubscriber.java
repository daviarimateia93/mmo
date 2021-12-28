package com.mmo.server.core.map;

import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.packet.Packet;

public interface MapPacketDispatchSubscriber {

    void onDispatch(Packet packet, Optional<UUID> target);
}
