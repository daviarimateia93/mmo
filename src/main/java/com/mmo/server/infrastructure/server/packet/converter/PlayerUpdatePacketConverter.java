package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PlayerUpdatePacketConverter implements PacketConverter<PlayerUpdatePacket> {

    @Override
    public PlayerUpdatePacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return PlayerUpdatePacket.builder()
                    .source(source)
                    .build();
        }
    }

    @Override
    public byte[] toBytes(PlayerUpdatePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            return writer.toBytes();
        }
    }
}