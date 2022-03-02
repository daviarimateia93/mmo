package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.infrastructure.map.PositionDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public final class PositionConverter {

    private PositionConverter() {

    }

    public static PositionDTO read(PacketReader reader) {
        PositionDTO dto = new PositionDTO();
        dto.setX(reader.readInt());
        dto.setZ(reader.readInt());

        return dto;
    }

    public static void write(PacketWriter writer, PositionDTO position) {
        writer.writeInt(position.getX());
        writer.writeInt(position.getZ());
    }
}
