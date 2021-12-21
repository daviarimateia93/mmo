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
public class GoodByePacket implements Packet {

    private static final String ALIAS = "GOOD_BYE";

    private final UUID source;

    @Builder
    private GoodByePacket(@NonNull UUID source) {
        this.source = source;
    }

    public static GoodByePacketBinaryBuilder binaryBuilder() {
        return new GoodByePacketBinaryBuilder();
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

    @Override
    public byte[] toBytes() {
        try (PacketWriter writer = new PacketWriter()) {
            return writer.toBytes();
        }
    }

    public static class GoodByePacketBinaryBuilder implements PacketBinaryBuilder<GoodByePacket> {

        @Override
        public GoodByePacket build(UUID source, byte[] bytes) {
            try (PacketReader reader = new PacketReader(bytes)) {
                return builder()
                        .source(source)
                        .build();
            }
        }
    }
}
