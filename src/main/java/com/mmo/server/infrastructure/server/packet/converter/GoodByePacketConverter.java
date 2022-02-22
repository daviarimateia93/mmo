package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.GoodByePacket;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketReaderConverter;
import com.mmo.server.infrastructure.server.packet.PacketWriter;
import com.mmo.server.infrastructure.server.packet.PacketWriterConverter;

public class GoodByePacketConverter
        implements PacketReaderConverter<GoodByePacket>, PacketWriterConverter<GoodByePacket> {

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
