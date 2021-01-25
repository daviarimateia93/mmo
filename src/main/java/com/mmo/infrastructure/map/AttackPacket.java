package com.mmo.infrastructure.map;

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
public class AttackPacket implements Packet {

    private static final String ALIAS = "ATTACK";

    private final UUID source;
    private final UUID target;

    private AttackPacket(UUID source, UUID target) {
        this.source = source;
        this.target = target;
    }

    public static AttackPacketBuilder builder() {
        return new AttackPacketBuilder();
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

    public static class AttackPacketBuilder implements PacketBuilder<AttackPacket> {

        @Override
        public AttackPacket build(UUID source, byte[] bytes) {
            try (PacketReader reader = new PacketReader(bytes)) {
                UUID target = reader.readUUID();
                return new AttackPacket(source, target);
            }
        }
    }
}
