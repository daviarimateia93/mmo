package com.mmo.server.core.packet;

@FunctionalInterface
public interface PacketHandler<T extends Packet> {

    void handle(T packet);
}
