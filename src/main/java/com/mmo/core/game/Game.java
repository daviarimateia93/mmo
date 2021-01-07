package com.mmo.core.game;

import java.util.Objects;

import com.mmo.core.looper.Looper;
import com.mmo.core.looper.LooperContext;
import com.mmo.core.map.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Game {

    private static Game instance;

    private Map map;
    private final Looper looper;

    public static Game getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Game();
        }

        return instance;
    }

    private Game() {
        looper = Looper.builder()
                .fps(120)
                .updater(this::update)
                .renderer(this::render)
                .build();
    }

    public Map getMap() {
        return map;
    }

    public void run(Map map) {
        if (isRunning()) {
            throw new GameAlreadyRunningException("Game is already running");
        }

        this.map = map;

        looper.run();
    }

    public void stop() {
        if (!isRunning()) {
            throw new GameNotRunningException("Game is not running");
        }

        looper.stop();
    }

    public boolean isRunning() {
        return looper.isRunning();
    }

    private void update(LooperContext context) {
        map.update(context);
    }

    private void render(LooperContext context) {

    }
}
