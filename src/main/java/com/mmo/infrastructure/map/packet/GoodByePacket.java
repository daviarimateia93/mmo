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
public class GoodByePacket implements Packet {

    private static final String ALIAS = "GOOD_BYE";

    private final UUID source;

    protected GoodByePacket(UUID source) {
        this.source = source;
    }

    public static GoodByePacketBuilder builder() {
        return new GoodByePacketBuilder();
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

    public static class GoodByePacketBuilder implements PacketBuilder<GoodByePacket> {

        @Override
        public GoodByePacket build(UUID source, byte[] bytes) {
            try (PacketReader reader = new PacketReader(bytes)) {
                return new GoodByePacket(source);
            }
        }
    }
}
