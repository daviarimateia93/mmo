package com.mmo.infrastructure.server.packet;

import java.util.UUID;

import com.mmo.core.packet.NetworkPacket;

public interface PacketConverter<T extends NetworkPacket> {

    T fromBytes(UUID source, byte[] bytes);
    
    byte[] toBytes(T packet);
}
