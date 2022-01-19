package com.mmo.server.core.map;

import com.mmo.server.core.game.Game;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Position extends Vertex {

    @Builder
    public Position(float x, float z) {
        super(x, z);
    }

    public boolean incrementX(float x) {
        float limit = this.x + x;

        while (this.x < limit) {
            if (!incrementX()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementX() {
        float newOne = x + 1;

        if (isInsideForbiddenArea(newOne, z)) {
            return false;
        }

        x++;
        return true;
    }

    public boolean incrementZ(float z) {
        float limit = this.z + z;

        while (this.z < limit) {
            if (!incrementZ()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementZ() {
        float newOne = z + 1;

        if (isInsideForbiddenArea(x, newOne)) {
            return false;
        }

        z++;
        return true;
    }

    public boolean decrementX(float x) {
        float limit = this.x - x;

        while (this.x > limit) {
            if (!decrementX()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementX() {
        float newOne = x - 1;

        if (isInsideForbiddenArea(newOne, z)) {
            return false;
        }

        x--;
        return true;
    }

    public boolean decrementZ(float z) {
        float limit = this.z - z;

        while (this.z > limit) {
            if (!decrementZ()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementZ() {
        float newOne = z - 1;

        if (isInsideForbiddenArea(x, newOne)) {
            return false;
        }

        z--;
        return true;
    }

    private boolean isInsideForbiddenArea(float x, float z) {
        return Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, z);
    }
}
