package com.mmo.server.infrastructure.animate;

import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.infrastructure.map.PositionDTO;

import lombok.Data;

@Data
public class AnimateDTO {

    private boolean alive;
    private boolean moving;
    private PositionDTO targetPosition;
    private boolean attacking;
    private UUID targetAnimate;

    public Optional<PositionDTO> getTargetPosition() {
        return Optional.ofNullable(targetPosition);
    }

    public Optional<UUID> getTargetAnimate() {
        return Optional.ofNullable(targetAnimate);
    }

    public static AnimateDTO of(Animate animate) {
        AnimateDTO dto = new AnimateDTO();
        dto.setAlive(animate.isAlive());
        dto.setMoving(animate.isMoving());

        animate.getTargetPosition()
                .map(PositionDTO::of)
                .ifPresent(dto::setTargetPosition);

        dto.setAttacking(animate.isAttacking());

        animate.getTargetAnimate()
                .map(Animate::getInstanceId)
                .ifPresent(dto::setTargetAnimate);

        return dto;
    }
}
