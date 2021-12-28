package com.mmo.server.core.looper;

@FunctionalInterface
public interface LooperUpdater {

    void update(LooperContext context);
}
