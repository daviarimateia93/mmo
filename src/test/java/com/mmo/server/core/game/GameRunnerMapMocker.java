package com.mmo.server.core.game;

import static org.mockito.Mockito.*;

import com.mmo.server.core.map.Map;

public class GameRunnerMapMocker {

    private static Map map;

    public static Map run() {
        map = mock(Map.class);

        new Thread() {
            @Override
            public void run() {
                Game.getInstance().run(map);
            }
        }.start();

        return map;
    }

    public static void stop() {
        Game.getInstance().stop();
    }
}
