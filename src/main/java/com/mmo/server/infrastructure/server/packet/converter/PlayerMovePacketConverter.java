package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketReaderConverter;
import com.mmo.server.infrastructure.server.packet.PacketWriter;
import com.mmo.server.infrastructure.server.packet.PacketWriterConverter;

public class PlayerMovePacketConverter
        implements PacketReaderConverter<PlayerMovePacket>, PacketWriterConverter<PlayerMovePacket> {

    @Override
    public PlayerMovePacket read(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return PlayerMovePacket.builder()
                    .source(source)
                    .target(PositionConverter.read(reader))
                    .build();
        }
    }

    @Override
    public byte[] write(PlayerMovePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            PositionConverter.write(writer, packet.getTarget());
            return writer.toBytes();
        }
    }
}