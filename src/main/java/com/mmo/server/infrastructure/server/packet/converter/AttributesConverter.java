package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public abstract class AttributesConverter {

    public static Attributes read(PacketReader reader) {
        return Attributes.builder()
                .hp(reader.readInt())
                .mp(reader.readInt())
                .attack(reader.readInt())
                .defense(reader.readInt())
                .magicDefense(reader.readInt())
                .hitRate(reader.readInt())
                .critical(reader.readInt())
                .dodgeRate(reader.readInt())
                .attackSpeed(reader.readInt())
                .moveSpeed(reader.readInt())
                .hpRecovery(reader.readInt())
                .mpRecovery(reader.readInt())
                .attackRange(reader.readInt())
                .build();
    }

    public static void write(PacketWriter writer, Attributes attributes) {
        writer.writeInt(attributes.getHP());
        writer.writeInt(attributes.getMP());
        writer.writeInt(attributes.getAttack());
        writer.writeInt(attributes.getDefense());
        writer.writeInt(attributes.getMagicDefense());
        writer.writeInt(attributes.getHitRate());
        writer.writeInt(attributes.getCritical());
        writer.writeInt(attributes.getDodgeRate());
        writer.writeInt(attributes.getAttackSpeed());
        writer.writeInt(attributes.getMoveSpeed());
        writer.writeInt(attributes.getHPRecovery());
        writer.writeInt(attributes.getMPRecovery());
        writer.writeInt(attributes.getAttackRange());
    }
}
