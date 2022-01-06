package com.mmo.server.core.map;

import com.mmo.server.core.game.Game;

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
        long limit = this.x + x;

        while (this.x < limit) {
            if (!incrementX()) {
                break;
            }
        }
    }

    public boolean incrementX() {
        long newOne = x + 1;

        if (!Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(newOne, y, z)) {
            x++;
            return true;
        }

        return false;
    }

    public void incrementY(long y) {
        long limit = this.y + y;

        while (this.y < limit) {
            if (!incrementY()) {
                break;
            }
        }
    }

    public boolean incrementY() {
        long newOne = y + 1;

        if (!Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, newOne, z)) {
            y++;
            return true;
        }

        return false;
    }

    public void incrementZ(long z) {
        long limit = this.z + z;

        while (this.z < limit) {
            if (!incrementZ()) {
                break;
            }
        }
    }

    public boolean incrementZ() {
        long newOne = z + 1;

        if (!Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, y, newOne)) {
            z++;
            return true;
        }

        return false;
    }

    public void decrementX(long x) {
        long limit = this.x - x;

        while (this.x > limit) {
            if (!decrementX()) {
                break;
            }
        }
    }

    public boolean decrementX() {
        long newOne = x - 1;

        if (!Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(newOne, y, z)) {
            x--;
            return true;
        }

        return false;
    }

    public void decrementY(long y) {
        long limit = this.y - y;

        while (this.y > limit) {
            if (!decrementY()) {
                break;
            }
        }
    }

    public boolean decrementY() {
        long newOne = y - 1;

        if (!Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, newOne, z)) {
            y--;
            return true;
        }

        return false;
    }

    public void decrementZ(long z) {
        long limit = this.z - z;

        while (this.z > limit) {
            if (!decrementZ()) {
                break;
            }
        }
    }

    public boolean decrementZ() {
        long newOne = z - 1;

        if (!Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, y, newOne)) {
            z--;
            return true;
        }

        return false;
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
