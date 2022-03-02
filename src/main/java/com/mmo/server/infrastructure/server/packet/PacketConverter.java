package com.mmo.server.infrastructure.server.packet;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.mmo.server.core.packet.NetworkPacket;

public interface PacketConverter<T extends NetworkPacket> {

    T read(UUID source, OffsetDateTime creation, byte[] bytes);

    byte[] write(T packet);
}
