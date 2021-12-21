package com.mmo.infrastructure.map.packet;

import java.util.UUID;

import com.mmo.core.map.Position;
import com.mmo.infrastructure.server.Packet;
import com.mmo.infrastructure.server.PacketBinaryBuilder;
import com.mmo.infrastructure.server.PacketReader;
import com.mmo.infrastructure.server.PacketWriter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MovePacket implements Packet {

    public static final String ALIAS = "MOVE";

    private final UUID source;
    private final Position target;

    @Builder
    private MovePacket(@NonNull UUID source, @NonNull Position target) {
        this.source = source;
        this.target = target;
    }

    public static MovePacketBinaryBuilder binaryBuilder() {
        return new MovePacketBinaryBuilder();
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

    @Override
    public byte[] toBytes() {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeLong(target.getX());
            writer.writeLong(target.getY());
            writer.writeLong(target.getZ());
            return writer.toBytes();
        }
    }

    public static class MovePacketBinaryBuilder implements PacketBinaryBuilder<MovePacket> {

        @Override
        public MovePacket build(UUID source, byte[] bytes) {
            try (PacketReader reader = new PacketReader(bytes)) {
                return builder()
                        .source(source)
                        .target(Position.builder()
                                .x(reader.readLong())
                                .y(reader.readLong())
                                .z(reader.readLong())
                                .build())
                        .build();
            }
        }
    }
}
