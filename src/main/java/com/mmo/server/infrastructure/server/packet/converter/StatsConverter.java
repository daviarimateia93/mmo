package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public abstract class StatsConverter {

    public static Stats read(PacketReader reader) {
        return Stats.builder()
                .strength(reader.readInt())
                .dexterity(reader.readInt())
                .intelligence(reader.readInt())
                .concentration(reader.readInt())
                .sense(reader.readInt())
                .charm(reader.readInt())
                .build();
    }

    public static void write(PacketWriter writer, Stats stats) {
        writer.writeInt(stats.getStrength());
        writer.writeInt(stats.getDexterity());
        writer.writeInt(stats.getIntelligence());
        writer.writeInt(stats.getConcentration());
        writer.writeInt(stats.getSense());
        writer.writeInt(stats.getCharm());
    }
}
