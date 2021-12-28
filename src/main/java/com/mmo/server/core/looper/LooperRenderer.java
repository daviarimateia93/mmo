package com.mmo.server.core.looper;

@FunctionalInterface
public interface LooperRenderer {

    void render(LooperContext context);
}
