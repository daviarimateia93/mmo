package com.mmo.infrastructure.server;

import java.util.UUID;

public interface PacketBinaryBuilder<T extends Packet> {

    T build(UUID source, byte[] bytes);
}
