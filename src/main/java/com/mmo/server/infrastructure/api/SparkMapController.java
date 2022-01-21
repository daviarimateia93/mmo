package com.mmo.server.infrastructure.api;

import static com.mmo.server.infrastructure.api.Spark.*;

import java.util.UUID;

import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.MapRepository;

import lombok.Builder;
import lombok.NonNull;
import spark.Request;
import spark.Response;

public class SparkMapController {

    private final MapRepository repository;

    @Builder
    private SparkMapController(@NonNull MapRepository repository) {
        this.repository = repository;

        get("/maps/:id", this::getById);
    }

    private Map getById(Request request, Response response) {
        UUID id = getUUIDParam("id", request, response);

        return repository.find(id)
                .orElseGet(() -> setStatus(404, response));
    }
}
