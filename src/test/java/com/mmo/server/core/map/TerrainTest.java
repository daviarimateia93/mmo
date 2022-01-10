package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

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
                                .bottomLeftVertex(new Vertex(10, 11, 2))
                                .bottomRightVertex(new Vertex(20, 11, 2))
                                .topLeftVertex(new Vertex(10, 21, 12))
                                .topRightVertex(new Vertex(20, 21, 12))
                                .build(),
                        Rectangle.builder()
                                .bottomLeftVertex(new Vertex(110, 111, 102))
                                .bottomRightVertex(new Vertex(120, 111, 102))
                                .topLeftVertex(new Vertex(110, 121, 112))
                                .topRightVertex(new Vertex(120, 121, 112))
                                .build()))
                .build();
    }

    @Test
    public void isInsideForbiddenArea() {
        Vertex vertex = new Vertex(15, 15, 6);

        boolean expected = true;
        boolean result = terrain.isInsideForbiddenArea(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void isOutsideForbiddenArea() {
        Vertex vertex = new Vertex(215, 215, 206);

        boolean expected = false;
        boolean result = terrain.isInsideForbiddenArea(vertex);

        assertThat(result, equalTo(expected));
    }
}
