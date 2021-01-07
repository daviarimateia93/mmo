package com.mmo.infrastructure.server;

public interface PacketBuilder<T extends Packet> {

    T build(byte[] bytes);
}
