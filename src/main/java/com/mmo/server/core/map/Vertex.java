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
    protected float z;

    public float getDistance(Vertex other) {
        return (x - other.getX()) * (x - other.getX()) + (z - other.getZ()) * (z - other.getZ());
    }

    public boolean isNearby(Vertex position, int ratio) {
        float minX = x - ratio;
        float maxX = x + ratio;
        float minZ = z - ratio;
        float maxZ = z + ratio;

        return position.getX() >= minX && position.getX() <= maxX
                && position.getZ() >= minZ && position.getZ() <= maxZ;
    }
}
