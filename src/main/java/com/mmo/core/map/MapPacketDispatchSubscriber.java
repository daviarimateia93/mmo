package com.mmo.core.map;

import java.util.Optional;
import java.util.UUID;

import com.mmo.core.packet.Packet;

public interface MapPacketDispatchSubscriber {

    void onDispatch(Packet packet, Optional<UUID> target);
}
