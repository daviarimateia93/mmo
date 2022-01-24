package com.mmo.server.core.map;

import com.mmo.server.core.game.Game;
import com.mmo.server.core.math.Vertex;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Position extends Vertex {

    @Builder
    public Position(int x, int z) {
        super(x, z);
    }

    public boolean incrementX(int x) {
        int limit = this.x + x;

        while (this.x < limit) {
            if (!incrementX()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementX() {
        int newOne = x + 1;

        if (isInsideForbiddenArea(newOne, z)) {
            return false;
        }

        x++;
        return true;
    }

    public boolean incrementZ(int z) {
        int limit = this.z + z;

        while (this.z < limit) {
            if (!incrementZ()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementZ() {
        int newOne = z + 1;

        if (isInsideForbiddenArea(x, newOne)) {
            return false;
        }

        z++;
        return true;
    }

    public boolean decrementX(int x) {
        int limit = this.x - x;

        while (this.x > limit) {
            if (!decrementX()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementX() {
        int newOne = x - 1;

        if (isInsideForbiddenArea(newOne, z)) {
            return false;
        }

        x--;
        return true;
    }

    public boolean decrementZ(int z) {
        int limit = this.z - z;

        while (this.z > limit) {
            if (!decrementZ()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementZ() {
        int newOne = z - 1;

        if (isInsideForbiddenArea(x, newOne)) {
            return false;
        }

        z--;
        return true;
    }

    private boolean isInsideForbiddenArea(int x, int z) {
        return Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, z);
    }
}
