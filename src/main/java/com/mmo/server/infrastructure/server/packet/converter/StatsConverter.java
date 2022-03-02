package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.infrastructure.animate.StatsDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public final class StatsConverter {

    private StatsConverter() {

    }

    public static StatsDTO read(PacketReader reader) {
        StatsDTO dto = new StatsDTO();
        dto.setStrength(reader.readInt());
        dto.setDexterity(reader.readInt());
        dto.setIntelligence(reader.readInt());
        dto.setConcentration(reader.readInt());
        dto.setSense(reader.readInt());
        dto.setCharm(reader.readInt());

        return dto;
    }

    public static void write(PacketWriter writer, StatsDTO stats) {
        writer.writeInt(stats.getStrength());
        writer.writeInt(stats.getDexterity());
        writer.writeInt(stats.getIntelligence());
        writer.writeInt(stats.getConcentration());
        writer.writeInt(stats.getSense());
        writer.writeInt(stats.getCharm());
    }
}
