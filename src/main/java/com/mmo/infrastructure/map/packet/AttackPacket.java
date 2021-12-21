package com.mmo.infrastructure.map.packet;

import java.util.UUID;

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
public class AttackPacket implements Packet {

    private static final String ALIAS = "ATTACK";

    private final UUID source;
    private final UUID target;

    @Builder
    private AttackPacket(@NonNull UUID source, @NonNull UUID target) {
        this.source = source;
        this.target = target;
    }

    public static AttackPacketBinaryBuilder binaryBuilder() {
        return new AttackPacketBinaryBuilder();
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

    @Override
    public byte[] toBytes() {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUUID(target);
            return writer.toBytes();
        }
    }

    public static class AttackPacketBinaryBuilder implements PacketBinaryBuilder<AttackPacket> {

        @Override
        public AttackPacket build(UUID source, byte[] bytes) {
            try (PacketReader reader = new PacketReader(bytes)) {
                return builder()
                        .source(source)
                        .target(reader.readUUID())
                        .build();
            }
        }
    }
}
