package com.mmo.server.infrastructure.server.packet;

import java.util.UUID;

import com.mmo.server.core.packet.NetworkPacket;

public interface PacketReaderConverter<T extends NetworkPacket> extends PacketConverter<T> {

    T fromBytes(UUID source, byte[] bytes);
}
