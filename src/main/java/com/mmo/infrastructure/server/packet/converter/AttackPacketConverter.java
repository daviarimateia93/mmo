package com.mmo.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.core.packet.AttackPacket;
import com.mmo.infrastructure.server.packet.PacketConverter;
import com.mmo.infrastructure.server.packet.PacketReader;
import com.mmo.infrastructure.server.packet.PacketWriter;

public class AttackPacketConverter implements PacketConverter<AttackPacket> {

    @Override
    public AttackPacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return AttackPacket.builder()
                    .source(source)
                    .target(reader.readUUID())
                    .build();
        }
    }

    @Override
    public byte[] toBytes(AttackPacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUUID(packet.getTarget());
            return writer.toBytes();
        }
    }
}