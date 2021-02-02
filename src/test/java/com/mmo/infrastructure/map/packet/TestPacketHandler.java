package com.mmo.infrastructure.map.packet;

import com.mmo.infrastructure.map.MapServer;
import com.mmo.infrastructure.server.TestPacket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TestPacketHandler implements PacketHandler<TestPacket> {

    private MapServer server;
    private TestPacket packet;

    @Override
    public void handle(MapServer server, TestPacket packet) {
        this.server = server;
        this.packet = packet;
    }
}
