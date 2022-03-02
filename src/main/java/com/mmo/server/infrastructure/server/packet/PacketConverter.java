package com.mmo.server.infrastructure.server.packet;

import java.util.UUID;

import com.mmo.server.core.packet.NetworkPacket;

public interface PacketConverter<T extends NetworkPacket> {

    T read(UUID source, byte[] bytes);

    byte[] write(T packet);
}
