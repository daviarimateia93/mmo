package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.map.Position;
import com.mmo.server.infrastructure.animate.AnimateDTO;
import com.mmo.server.infrastructure.map.PositionDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public final class AnimateConverter {

    public static final UUID UUID_NULL = UUID.fromString("9d84ce45-11bd-41cb-b16f-072863c03e9c");

    public static final PositionDTO POSITION_NULL = PositionDTO.of(Position.builder()
            .x(Integer.MIN_VALUE)
            .z(Integer.MAX_VALUE)
            .build());

    private AnimateConverter() {

    }

    public static AnimateDTO read(PacketReader reader) {
        AnimateDTO dto = new AnimateDTO();
        dto.setAlive(reader.readBoolean());
        dto.setMoving(reader.readBoolean());

        PositionDTO targetPosition = PositionConverter.read(reader);
        if (!POSITION_NULL.equals(targetPosition)) {
            dto.setTargetPosition(targetPosition);
        }

        dto.setAttacking(reader.readBoolean());

        UUID targetAnimate = reader.readUUID();
        if (!UUID_NULL.equals(targetAnimate)) {
            dto.setTargetAnimate(targetAnimate);
        }

        return dto;
    }

    public static void write(PacketWriter writer, AnimateDTO animate) {
        // alive session
        writer.writeBoolean(animate.isAlive());

        // move session
        writer.writeBoolean(animate.isMoving());
        PositionConverter.write(writer, animate.getTargetPosition().orElse(POSITION_NULL));

        // attack session
        writer.writeBoolean(animate.isAttacking());
        writer.writeUUID(animate.getTargetAnimate()
                .orElse(UUID_NULL));
    }
}
