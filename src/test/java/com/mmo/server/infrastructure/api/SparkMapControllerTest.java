package com.mmo.server.infrastructure.api;

import static com.mashape.unirest.http.Unirest.*;
import static com.mmo.server.infrastructure.api.Spark.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.MapRepository;
import com.mmo.server.core.map.Terrain;

public class SparkMapControllerTest {

    private static MapRepository repository;

    @BeforeAll
    public static void setup() {
        repository = mock(MapRepository.class);

        SparkMapController.builder()
                .repository(repository)
                .build();
    }

    @Test
    public void getById() throws UnirestException {
        Map expected = Map.builder()
                .id(UUID.randomUUID())
                .name("name")
                .description("description")
                .nearbyRatio(10)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
                .build();

        when(repository.find(expected.getId())).thenReturn(Optional.of(expected));

        Map result = fromJson(get("http://localhost:4567/maps/" + expected.getId())
                .asString()
                .getBody(), Map.class);

        assertThat(result, equalTo(expected));
    }
}
