package com.mmo.server.infrastructure.map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Terrain;
import com.mmo.server.infrastructure.mongo.MongoServer;

public class MongoMapRepositoryTest {

    private static MongoMapRepository repository;

    @BeforeAll
    public static void setup() {
        MongoServer.getInstance().start();

        repository = new MongoMapRepository();
    }

    @AfterAll
    public static void clear() {
        // MongoServer.getInstance().stop();
    }

    @Test
    public void findAndPersist() {
        Map map = Map.builder()
                .id(UUID.randomUUID())
                .name("name")
                .description("description")
                .nearbyRatio(1)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
                .build();

        assertThat(repository.find(map.getId()), is(Optional.empty()));

        repository.persist(map);

        assertThat(repository.find(map.getId()), is(Optional.of(map)));
    }
}
