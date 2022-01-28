package com.mmo.server.infrastructure.api;

import static com.mmo.server.infrastructure.api.Spark.*;

import java.util.UUID;

import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.infrastructure.player.PlayerEntity;
import com.mmo.server.infrastructure.security.Authenticator;

import lombok.Builder;
import lombok.NonNull;
import spark.Request;
import spark.Response;

public class SparkPlayerController {

    private final Authenticator authenticator;
    private final PlayerRepository repository;

    @Builder
    private SparkPlayerController(
            @NonNull Authenticator authenticator,
            @NonNull PlayerRepository repository) {

        this.authenticator = authenticator;
        this.repository = repository;

        post("/players/:id", this::getPlayer);
    }

    private PlayerEntity getPlayer(Request request, Response response) {
        UUID id = getUUIDParam("id", request, response);
        String userName = request.headers("x-userName");
        String userPassword = request.headers("x-userPassword");

        if (!authenticator.authenticate(userName, userPassword, id)) {
            return setStatus(401, response);
        }

        return repository.find(id)
                .map(PlayerEntity::of)
                .orElseGet(() -> setStatus(404, response));
    }
}
