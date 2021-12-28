package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.AnimateAttackPacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class AnimateAttackPacketConverter implements PacketConverter<AnimateAttackPacket> {

    @Override
    public AnimateAttackPacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return AnimateAttackPacket.builder()
                    .source(source)
                    .target(reader.readUUID())
                    .build();
        }
    }

    @Override
    public byte[] toBytes(AnimateAttackPacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUUID(packet.getTarget());
            return writer.toBytes();
        }
    }
}