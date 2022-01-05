package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TerrainTest {

    private static Terrain terrain;

    @BeforeAll
    public static void setup() {
        terrain = Terrain.builder()
                .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                .forbiddenAreas(List.of(
                        Rectangle.builder()
                                .bottomLeftPosition(Position.builder()
                                        .x(10L)
                                        .y(11L)
                                        .z(2L)
                                        .build())
                                .bottomRightPosition(Position.builder()
                                        .x(20L)
                                        .y(11L)
                                        .z(2L)
                                        .build())
                                .topLeftPosition(Position.builder()
                                        .x(10L)
                                        .y(21L)
                                        .z(12L)
                                        .build())
                                .topRightPosition(Position.builder()
                                        .x(20L)
                                        .y(21L)
                                        .z(12L)
                                        .build())
                                .build(),
                        Rectangle.builder()
                                .bottomLeftPosition(Position.builder()
                                        .x(110L)
                                        .y(111L)
                                        .z(102L)
                                        .build())
                                .bottomRightPosition(Position.builder()
                                        .x(120L)
                                        .y(111L)
                                        .z(102L)
                                        .build())
                                .topLeftPosition(Position.builder()
                                        .x(110L)
                                        .y(121L)
                                        .z(112L)
                                        .build())
                                .topRightPosition(Position.builder()
                                        .x(120L)
                                        .y(121L)
                                        .z(112L)
                                        .build())
                                .build()))
                .build();
    }

    @Test
    public void isInsideForbiddenArea() {
        Position position = Position.builder()
                .x(15L)
                .y(15L)
                .z(6L)
                .build();

        boolean expected = true;
        boolean result = terrain.isInsideForbiddenArea(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void isOutsideForbiddenArea() {
        Position position = Position.builder()
                .x(215L)
                .y(215L)
                .z(206L)
                .build();

        boolean expected = false;
        boolean result = terrain.isInsideForbiddenArea(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getForbiddenArea() {
        Position position = Position.builder()
                .x(15L)
                .y(15L)
                .z(6L)
                .build();

        Optional<Rectangle> expected = Optional.of(terrain.getForbiddenAreas().iterator().next());
        Optional<Rectangle> result = terrain.getForbiddenArea(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getForbiddenAreaWhenNotInside() {
        Position position = Position.builder()
                .x(215L)
                .y(215L)
                .z(206L)
                .build();

        Optional<Rectangle> expected = Optional.empty();
        Optional<Rectangle> result = terrain.getForbiddenArea(position);

        assertThat(result, equalTo(expected));
    }
}
