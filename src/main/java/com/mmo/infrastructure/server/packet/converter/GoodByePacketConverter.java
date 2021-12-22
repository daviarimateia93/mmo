package com.mmo.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.core.packet.GoodByePacket;
import com.mmo.infrastructure.server.packet.PacketConverter;
import com.mmo.infrastructure.server.packet.PacketReader;
import com.mmo.infrastructure.server.packet.PacketWriter;

public class GoodByePacketConverter implements PacketConverter<GoodByePacket> {

    @Override
    public GoodByePacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return GoodByePacket.builder()
                    .source(source)
                    .build();
        }
    }

    @Override
    public byte[] toBytes(GoodByePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            return writer.toBytes();
        }
    }
}
