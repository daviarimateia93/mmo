package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.infrastructure.animate.AttributesDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public final class AttributesConverter {

    private AttributesConverter() {

    }

    public static AttributesDTO read(PacketReader reader) {
        AttributesDTO dto = new AttributesDTO();
        dto.setHP(reader.readInt());
        dto.setMP(reader.readInt());
        dto.setAttack(reader.readInt());
        dto.setDefense(reader.readInt());
        dto.setMagicDefense(reader.readInt());
        dto.setHitRate(reader.readInt());
        dto.setCritical(reader.readInt());
        dto.setDodgeRate(reader.readInt());
        dto.setAttackSpeed(reader.readInt());
        dto.setMoveSpeed(reader.readInt());
        dto.setHPRecovery(reader.readInt());
        dto.setMPRecovery(reader.readInt());
        dto.setAttackRange(reader.readInt());

        return dto;
    }

    public static void write(PacketWriter writer, AttributesDTO attributes) {
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
