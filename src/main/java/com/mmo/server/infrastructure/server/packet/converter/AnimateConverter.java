package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.map.Position;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public final class AnimateConverter {

    public static final UUID UUID_NULL = UUID.fromString("9d84ce45-11bd-41cb-b16f-072863c03e9c");

    public static final Position POSITION_NULL = Position.builder()
            .x(Integer.MIN_VALUE)
            .z(Integer.MAX_VALUE)
            .build();

    private AnimateConverter() {

    }

    public static void write(PacketWriter writer, Animate animate) {
        // alive session
        writer.writeBoolean(animate.isAlive());

        // move session
        writer.writeBoolean(animate.isMoving());
        PositionConverter.write(writer, animate.getTargetPosition().orElse(POSITION_NULL));

        // attack session
        writer.writeBoolean(animate.isAttacking());
        writer.writeUUID(animate.getTargetAnimate()
                .map(Animate::getInstanceId)
                .orElse(UUID_NULL));
    }
}
