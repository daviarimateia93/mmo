package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.PlayerAttackPacket;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketReaderConverter;
import com.mmo.server.infrastructure.server.packet.PacketWriter;
import com.mmo.server.infrastructure.server.packet.PacketWriterConverter;

public class PlayerAttackPacketConverter
        implements PacketReaderConverter<PlayerAttackPacket>, PacketWriterConverter<PlayerAttackPacket> {

    public PlayerAttackPacket read(UUID source, byte[] bytes) {
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