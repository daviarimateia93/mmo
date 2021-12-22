package com.mmo.infrastructure.server.packet;

import java.util.UUID;

import com.mmo.core.packet.Packet;

public interface PacketConverter<T extends Packet> {

    T fromBytes(UUID source, byte[] bytes);
    
    byte[] toBytes(T packet);
}
