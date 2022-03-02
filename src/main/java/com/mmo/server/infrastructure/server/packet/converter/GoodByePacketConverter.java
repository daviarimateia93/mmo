package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.GoodByePacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class GoodByePacketConverter implements PacketConverter<GoodByePacket> {

    @Override
    public GoodByePacket read(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return GoodByePacket.builder()
                    .source(source)
                    .build();
        }
    }

    @Override
    public byte[] write(GoodByePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            return writer.toBytes();
        }
    }
}
