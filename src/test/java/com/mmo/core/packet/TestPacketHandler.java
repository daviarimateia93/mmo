package com.mmo.core.packet;

import com.mmo.core.map.Map;
import com.mmo.infrastructure.server.TestPacket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TestPacketHandler implements PacketHandler<TestPacket> {

    private Map map;
    private TestPacket packet;

    @Override
    public void handle(Map map, TestPacket packet) {
        this.map = map;
        this.packet = packet;
    }
}
