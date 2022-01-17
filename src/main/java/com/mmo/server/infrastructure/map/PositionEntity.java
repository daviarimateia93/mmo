package com.mmo.server.infrastructure.map;

import com.mmo.server.core.map.Position;

import lombok.Data;

@Data
public class PositionEntity {

    private Float x;
    private Float y;
    private Long z;

    public static PositionEntity of(Position position) {
        PositionEntity entity = new PositionEntity();
        entity.setX(position.getX());
        entity.setY(position.getY());

        return entity;
    }

    public Position toPosition() {
        return Position.builder()
                .x(getX())
                .y(getY())
                .build();
    }
}