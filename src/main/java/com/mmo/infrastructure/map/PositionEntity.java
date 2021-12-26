package com.mmo.infrastructure.map;

import com.mmo.core.map.Position;

import lombok.Data;

@Data
public class PositionEntity {

    private Long x;
    private Long y;
    private Long z;

    public static PositionEntity of(Position position) {
        PositionEntity entity = new PositionEntity();
        entity.setX(position.getX());
        entity.setY(position.getY());
        entity.setZ(position.getZ());

        return entity;
    }

    public Position toPosition() {
        return Position.builder()
                .x(getX())
                .y(getY())
                .z(getZ())
                .build();
    }
}