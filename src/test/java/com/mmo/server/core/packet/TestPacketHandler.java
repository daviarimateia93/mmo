package com.mmo.server.core.packet;

import com.mmo.server.infrastructure.server.TestPacket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TestPacketHandler implements PacketHandler<TestPacket> {

    private TestPacket packet;

    @Override
    public void handle(TestPacket packet) {
        this.packet = packet;
    }
}
