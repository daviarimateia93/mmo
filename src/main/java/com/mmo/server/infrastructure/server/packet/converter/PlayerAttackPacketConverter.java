package com.mmo.server.infrastructure.server.packet.converter;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.mmo.server.core.packet.PlayerAttackPacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PlayerAttackPacketConverter implements PacketConverter<PlayerAttackPacket> {

    public PlayerAttackPacket read(UUID source, OffsetDateTime creation, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return PlayerAttackPacket.builder()
                    .source(source)
                    .target(reader.readUUID())
                    .build();
        }
    }

    @Override
    public byte[] write(PlayerAttackPacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUUID(packet.getTarget());
            return writer.toBytes();
        }
    }
}