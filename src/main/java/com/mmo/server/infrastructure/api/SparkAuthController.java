package com.mmo.server.infrastructure.api;

import static com.mmo.server.infrastructure.api.Spark.*;

import java.util.UUID;

import com.mmo.server.core.player.Player;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.infrastructure.security.Encryptor;
import com.mmo.server.infrastructure.security.TokenData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import spark.Request;
import spark.Response;

public class SparkAuthController {

    private final PlayerRepository repository;
    private final Encryptor encryptor;

    @Builder
    private SparkAuthController(@NonNull PlayerRepository repository, @NonNull Encryptor encryptor) {
        this.repository = repository;
        this.encryptor = encryptor;

        post("/auth/:id", this::getToken);
    }

    private TokenResponseDTO getToken(Request request, Response response) {
        UUID id = getUUIDParam("id", request, response);

        return repository.find(id)
                .map(Player::getInstanceId)
                .map(TokenData::create)
                .map(this::encrypt)
                .orElseGet(() -> setStatus(401, response));
    }

    private TokenResponseDTO encrypt(TokenData tokenData) {
        String token = encryptor.encrypt(tokenData.getToken());

        return new TokenResponseDTO(token);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TokenResponseDTO {

        private String token;
    }
}
