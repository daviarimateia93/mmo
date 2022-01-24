package com.mmo.server.core.math;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Vertex {

    protected int x;
    protected int z;

    public int getDistance(Vertex other) {
        return (x - other.getX()) * (x - other.getX()) + (z - other.getZ()) * (z - other.getZ());
    }

    public boolean isNearby(Vertex position, int ratio) {
        int minX = x - ratio;
        int maxX = x + ratio;
        int minZ = z - ratio;
        int maxZ = z + ratio;

        return position.getX() >= minX && position.getX() <= maxX
                && position.getZ() >= minZ && position.getZ() <= maxZ;
    }
}
