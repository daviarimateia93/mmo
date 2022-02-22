package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PlayerMovePacketConverter implements PacketConverter<PlayerMovePacket> {

    @Override
    public PlayerMovePacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return PlayerMovePacket.builder()
                    .source(source)
                    .target(Position.builder()
                            .x(reader.readInt())
                            .z(reader.readInt())
                            .build())
                    .build();
        }
    }

    @Override
    public byte[] toBytes(PlayerMovePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeInt(packet.getTarget().getX());
            writer.writeInt(packet.getTarget().getZ());
            return writer.toBytes();
        }
    }
}