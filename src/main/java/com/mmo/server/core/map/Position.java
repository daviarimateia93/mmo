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
}
