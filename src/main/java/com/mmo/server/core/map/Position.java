package com.mmo.server.core.map;

import com.mmo.server.core.game.Game;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Position extends Vertex {

    @Builder
    public Position(long x, long y, long z) {
        super(x, y, z);
    }

    public boolean incrementX(long x) {
        long limit = this.x + x;

        while (this.x < limit) {
            if (!incrementX()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementX() {
        long newOne = x + 1;

        if (isInsideForbiddenArea(newOne, y, z)) {
            return false;
        }

        x++;
        return true;
    }

    public boolean incrementY(long y) {
        long limit = this.y + y;

        while (this.y < limit) {
            if (!incrementY()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementY() {
        long newOne = y + 1;

        if (isInsideForbiddenArea(x, newOne, z)) {
            return false;
        }

        y++;
        return true;
    }

    public boolean incrementZ(long z) {
        long limit = this.z + z;

        while (this.z < limit) {
            if (!incrementZ()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementZ() {
        long newOne = z + 1;

        if (isInsideForbiddenArea(x, y, newOne)) {
            return false;
        }

        z++;
        return true;
    }

    public boolean decrementX(long x) {
        long limit = this.x - x;

        while (this.x > limit) {
            if (!decrementX()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementX() {
        long newOne = x - 1;

        if (isInsideForbiddenArea(newOne, y, z)) {
            return false;
        }

        x--;
        return true;
    }

    public boolean decrementY(long y) {
        long limit = this.y - y;

        while (this.y > limit) {
            if (!decrementY()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementY() {
        long newOne = y - 1;

        if (isInsideForbiddenArea(x, newOne, z)) {
            return false;
        }

        y--;
        return true;
    }

    public boolean decrementZ(long z) {
        long limit = this.z - z;

        while (this.z > limit) {
            if (!decrementZ()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementZ() {
        long newOne = z - 1;

        if (isInsideForbiddenArea(x, y, newOne)) {
            return false;
        }

        z--;
        return true;
    }

    private boolean isInsideForbiddenArea(long x, long y, long z) {
        return Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, y, z);
    }
}
