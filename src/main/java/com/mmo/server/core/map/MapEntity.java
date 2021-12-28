package com.mmo.server.core.map;

import com.mmo.server.core.looper.LooperUpdater;

public interface MapEntity extends MapInstance, LooperUpdater {

    String getName();

    Position getPosition();
}
