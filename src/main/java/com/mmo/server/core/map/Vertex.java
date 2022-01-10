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

    protected long x;
    protected long y;
    protected long z;

    public boolean isNearby(Vertex position, int ratio) {
        long minX = x - ratio;
        long maxX = x + ratio;
        long minY = y - ratio;
        long maxY = y + ratio;
        long minZ = z - ratio;
        long maxZ = z + ratio;

        return position.getX() >= minX && position.getX() <= maxX
                && position.getY() >= minY && position.getY() <= maxY
                && position.getZ() >= minZ && position.getZ() <= maxZ;
    }
}
