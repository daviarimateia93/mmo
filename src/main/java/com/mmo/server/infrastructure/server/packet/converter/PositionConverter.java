package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.core.map.Position;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public abstract class PositionConverter {

    public static Position read(PacketReader reader) {
        return Position.builder()
                .x(reader.readInt())
                .z(reader.readInt())
                .build();
    }

    public static void write(PacketWriter writer, Position position) {
        writer.writeInt(position.getX());
        writer.writeInt(position.getZ());
    }
}
