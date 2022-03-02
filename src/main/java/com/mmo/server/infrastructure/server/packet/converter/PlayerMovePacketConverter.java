package com.mmo.server.infrastructure.server.packet.converter;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.infrastructure.map.PositionDTO;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PlayerMovePacketConverter implements PacketConverter<PlayerMovePacket> {

    @Override
    public PlayerMovePacket read(UUID source, OffsetDateTime creation, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return PlayerMovePacket.builder()
                    .source(source)
                    .target(PositionConverter.read(reader).toPosition())
                    .build();
        }
    }

    @Override
    public byte[] write(PlayerMovePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            PositionConverter.write(writer, PositionDTO.of(packet.getTarget()));
            return writer.toBytes();
        }
    }
}