package com.mmo.server.infrastructure.map;

import com.mmo.server.core.map.Position;

import lombok.Data;

@Data
public class PositionDTO {

    private Integer x;
    private Integer z;

    public static PositionDTO of(Position position) {
        PositionDTO dto = new PositionDTO();
        dto.setX(position.getX());
        dto.setZ(position.getZ());

        return dto;
    }

    public Position toPosition() {
        return Position.builder()
                .x(getX())
                .z(getZ())
                .build();
    }
}