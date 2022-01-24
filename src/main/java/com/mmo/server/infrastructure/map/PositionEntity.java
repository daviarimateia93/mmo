package com.mmo.server.infrastructure.map;

import com.mmo.server.core.map.Position;

import lombok.Data;

@Data
public class PositionEntity {

    private Integer x;
    private Integer z;

    public static PositionEntity of(Position position) {
        PositionEntity entity = new PositionEntity();
        entity.setX(position.getX());
        entity.setZ(position.getZ());

        return entity;
    }

    public Position toPosition() {
        return Position.builder()
                .x(getX())
                .z(getZ())
                .build();
    }
}