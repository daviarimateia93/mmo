package com.mmo.infrastructure.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.core.game.Game;
import com.mmo.core.map.Map;

public class MapServer {

    private static Logger logger = LoggerFactory.getLogger(MapServer.class);

    public static void main(String... args) {
        logger.info("Starting server");

        Map map = Map.builder()
                .name("adventure_plains")
                .description("Located at the southern end, these plains were quiet and peaceful.")
                .nearbyRatio(10)
                .build();

        Game.getInstance().run(map);
    }
}
