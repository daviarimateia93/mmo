package com.mmo.infrastructure.map.packet;

import java.util.UUID;

import com.mmo.infrastructure.server.Packet;
import com.mmo.infrastructure.server.PacketBuilder;
import com.mmo.infrastructure.server.PacketReader;
import com.mmo.infrastructure.server.PacketWriter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class HelloPacket implements Packet {

    private static final String ALIAS = "HELLO";

    private final UUID source;

    private HelloPacket(UUID source) {
        this.source = source;
    }

    public static HelloPacketBuilder builder() {
        return new HelloPacketBuilder();
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

    public static class HelloPacketBuilder implements PacketBuilder<HelloPacket> {

        @Override
        public HelloPacket build(UUID source, byte[] bytes) {
            try (PacketReader reader = new PacketReader(bytes)) {
                return new HelloPacket(source);
            }
        }
    }
}
