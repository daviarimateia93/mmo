package com.mmo.server.core.map;

import com.mmo.server.core.game.Game;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Position extends Vertex {

    @Builder
    public Position(float x, float y) {
        super(x, y);
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

        if (isInsideForbiddenArea(newOne, y)) {
            return false;
        }

        x++;
        return true;
    }

    public boolean incrementY(float y) {
        float limit = this.y + y;

        while (this.y < limit) {
            if (!incrementY()) {
                return false;
            }
        }

        return true;
    }

    public boolean incrementY() {
        float newOne = y + 1;

        if (isInsideForbiddenArea(x, newOne)) {
            return false;
        }

        y++;
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

        if (isInsideForbiddenArea(newOne, y)) {
            return false;
        }

        x--;
        return true;
    }

    public boolean decrementY(float y) {
        float limit = this.y - y;

        while (this.y > limit) {
            if (!decrementY()) {
                return false;
            }
        }

        return true;
    }

    public boolean decrementY() {
        float newOne = y - 1;

        if (isInsideForbiddenArea(x, newOne)) {
            return false;
        }

        y--;
        return true;
    }

    private boolean isInsideForbiddenArea(float x, float y) {
        return Game.getInstance().getMap()
                .getTerrain()
                .isInsideForbiddenArea(x, y);
    }
}
