package com.mmo.server.core.map;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Position {

    private Long x;
    private Long y;
    private Long z;

    @Builder
    private Position(@NonNull Long x, @NonNull Long y, @NonNull Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void incrementX(long x) {
        this.x += x;
    }

    public void incrementX() {
        x++;
    }

    public void incrementY(long y) {
        this.y += y;
    }

    public void incrementY() {
        y++;
    }

    public void incrementZ(long z) {
        this.z += z;
    }

    public void incrementZ() {
        z++;
    }

    public void decrementX(long x) {
        this.x -= x;
    }

    public void decrementX() {
        this.x--;
    }

    public void decrementY(long y) {
        this.y -= y;
    }

    public void decrementY() {
        this.y--;
    }

    public void decrementZ(long z) {
        this.z -= z;
    }

    public void decrementZ() {
        this.z--;
    }

    public boolean isNearby(Position position, int ratio) {
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
