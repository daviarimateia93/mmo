package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.game.Game;
import com.mmo.server.core.map.Map;

public abstract class PacketHandler {

    protected Map getMap() {
        return Game.getInstance().getMap();
    }
}
