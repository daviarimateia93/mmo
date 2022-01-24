package com.mmo.server.core.game;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Terrain;

public class GameRunnerMapMocker {

    private static Terrain terrain;
    private static Map map;

    public static Map run() {
        terrain = mock(Terrain.class);
        map = mock(Map.class);

        when(map.getTerrain()).thenReturn(terrain);
        when(terrain.isInsideForbiddenArea(anyInt(), anyInt())).thenReturn(false);

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
