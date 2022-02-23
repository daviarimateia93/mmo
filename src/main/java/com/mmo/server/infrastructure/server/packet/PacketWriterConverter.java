package com.mmo.server.infrastructure.server.packet;

import com.mmo.server.core.packet.NetworkPacket;

public interface PacketWriterConverter<T extends NetworkPacket> extends PacketConverter<T> {

    byte[] write(T packet);
}
