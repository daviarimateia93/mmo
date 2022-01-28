package com.mmo.server.infrastructure.api;

import com.mmo.server.core.map.MapRepository;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.core.user.UserRepository;
import com.mmo.server.infrastructure.map.MongoMapRepository;
import com.mmo.server.infrastructure.player.MongoPlayerRepository;
import com.mmo.server.infrastructure.security.Authenticator;
import com.mmo.server.infrastructure.user.MongoUserRepository;

public class SparkApi {

    private SparkApi() {
        UserRepository userRepository = new MongoUserRepository();
        PlayerRepository playerRepository = new MongoPlayerRepository();
        MapRepository mapRepository = new MongoMapRepository();

        Authenticator authenticator = Authenticator.builder()
                .userRepository(userRepository)
                .playerRepository(playerRepository)
                .build();

        SparkMapController.builder()
                .repository(mapRepository)
                .build();

        SparkPlayerController.builder()
                .repository(playerRepository)
                .authenticator(authenticator)
                .build();
    }

    public static void main(String... args) {
        new SparkApi();
    }
}
