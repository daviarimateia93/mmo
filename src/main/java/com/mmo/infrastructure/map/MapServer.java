package com.mmo.infrastructure.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.game.Game;
import com.mmo.core.map.Map;

public class MapServer {

    private static Logger logger = LoggerFactory.getLogger(MapServer.class);

    private final Map map;

    private MapServer() {
        logger.info("Starting server");

        map = Map.builder()
                .name("adventure_plains")
                .description("Located at the southern end, these plains were quiet and peaceful.")
                .nearbyRatio(10)
                .build();

        Game.getInstance().run(map);
    }

    public static void main(String... args) {
        new MapServer();
    }
}
