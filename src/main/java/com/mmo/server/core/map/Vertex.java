package com.mmo.server.core.map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Vertex {

    protected float x;
    protected float y;

    public float getDistance(Vertex other) {
        return (x - other.getX()) * (x - other.getX()) + (y - other.getY()) * (y - other.getY());
    }

    public boolean isNearby(Vertex position, int ratio) {
        float minX = x - ratio;
        float maxX = x + ratio;
        float minY = y - ratio;
        float maxY = y + ratio;

        return position.getX() >= minX && position.getX() <= maxX
                && position.getY() >= minY && position.getY() <= maxY;
    }
}
