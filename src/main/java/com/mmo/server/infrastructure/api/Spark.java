package com.mmo.server.infrastructure.api;

import java.util.Objects;
import java.util.UUID;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;

public abstract class Spark {

    private static final Gson gson = new Gson();

    public static void get(String path, Route route) {
        spark.Spark.get(path, (request, response) -> toJson(route.handle(request, response)));
    }

    public static void post(String path, Route route) {
        spark.Spark.post(path, (request, response) -> toJson(route.handle(request, response)));
    }

    public static void put(String path, Route route) {
        spark.Spark.put(path, (request, response) -> toJson(route.handle(request, response)));
    }

    public static void delete(String path, Route route) {
        spark.Spark.delete(path, (request, response) -> toJson(route.handle(request, response)));
    }

    public static UUID getUUIDParam(String param, Request request, Response response) {
        String uuid = request.params(param);

        if (Objects.isNull(uuid)) {
            return setStatus(400, response);
        }

        UUID parsed;

        try {
            parsed = UUID.fromString(uuid);
        } catch (Exception exception) {
            return setStatus(400, response);
        }

        return parsed;
    }

    public static <T> T getBody(Class<T> type, Request request, Response response) {
        try {
            String body = request.body();
            return fromJson(body, type);
        } catch (Exception exception) {
            return setStatus(400, response);
        }
    }

    public static <T> T setStatus(int status, Response response) {
        response.status(status);
        return null;
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
