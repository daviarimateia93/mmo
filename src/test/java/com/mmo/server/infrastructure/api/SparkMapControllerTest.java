package com.mmo.server.infrastructure.api;

import static com.mashape.unirest.http.Unirest.get;
import static com.mmo.server.infrastructure.api.Spark.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.mmo.server.infrastructure.map.MapDTO;

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
        Map map = Map.builder()
                .id(UUID.randomUUID())
                .name("name")
                .description("description")
                .nearbyRatio(10)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
                .build();

        when(repository.find(map.getId())).thenReturn(Optional.of(map));

        MapDTO expected = MapDTO.of(map);
        MapDTO result = fromJson(get("http://localhost:4567/maps/" + map.getId())
                .asString()
                .getBody(), MapDTO.class);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getByIdReturns404() throws UnirestException {
        when(repository.find(any())).thenReturn(Optional.empty());

        int expected = 404;
        int result = get("http://localhost:4567/maps/" + UUID.randomUUID())
                .asString()
                .getStatus();

        assertThat(result, equalTo(expected));
    }
}
