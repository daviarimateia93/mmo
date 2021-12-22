package com.mmo.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.core.map.Position;
import com.mmo.core.packet.MovePacket;
import com.mmo.infrastructure.server.packet.PacketConverter;
import com.mmo.infrastructure.server.packet.PacketReader;
import com.mmo.infrastructure.server.packet.PacketWriter;

public class MovePacketConverter implements PacketConverter<MovePacket> {

    @Override
    public MovePacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return MovePacket.builder()
                    .source(source)
                    .target(Position.builder()
                            .x(reader.readLong())
                            .y(reader.readLong())
                            .z(reader.readLong())
                            .build())
                    .build();
        }
    }

    @Override
    public byte[] toBytes(MovePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeLong(packet.getTarget().getX());
            writer.writeLong(packet.getTarget().getY());
            writer.writeLong(packet.getTarget().getZ());
            return writer.toBytes();
        }
    }
}