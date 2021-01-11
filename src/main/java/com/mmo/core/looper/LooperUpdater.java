package com.mmo.core.looper;

@FunctionalInterface
public interface LooperUpdater {

    void update(LooperContext context);
}
