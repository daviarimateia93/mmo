package com.mmo.core.map;

import com.mmo.core.looper.LooperUpdater;

public interface MapEntity extends MapInstance, LooperUpdater {

    String getName();

    Position getPosition();
}
