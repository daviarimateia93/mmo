package com.mmo.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.core.packet.AnimateDiePacket;
import com.mmo.infrastructure.server.packet.PacketConverter;
import com.mmo.infrastructure.server.packet.PacketReader;
import com.mmo.infrastructure.server.packet.PacketWriter;

public class AnimateDiePacketConverter implements PacketConverter<AnimateDiePacket> {

    @Override
    public AnimateDiePacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return AnimateDiePacket.builder()
                    .source(source)
                    .killedBy(reader.readUUID())
                    .build();
        }
    }

    @Override
    public byte[] toBytes(AnimateDiePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUUID(packet.getKilledBy());
            return writer.toBytes();
        }
    }
}