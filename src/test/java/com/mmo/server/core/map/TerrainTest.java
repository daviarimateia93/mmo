package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.math.Rectangle;
import com.mmo.server.core.math.Vertex;

public class TerrainTest {

    private static Terrain terrain;

    @BeforeAll
    public static void setup() {
        terrain = Terrain.builder()
                .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                .forbiddenAreas(List.of(
                        Rectangle.builder()
                                .bottomLeftVertex(new Vertex(10, 11))
                                .bottomRightVertex(new Vertex(20, 11))
                                .topLeftVertex(new Vertex(10, 21))
                                .topRightVertex(new Vertex(20, 21))
                                .build(),
                        Rectangle.builder()
                                .bottomLeftVertex(new Vertex(110, 111))
                                .bottomRightVertex(new Vertex(120, 111))
                                .topLeftVertex(new Vertex(110, 121))
                                .topRightVertex(new Vertex(120, 121))
                                .build()))
                .build();
    }

    @Test
    public void isInsideForbiddenArea() {
        Vertex vertex = new Vertex(15, 15);

        boolean expected = true;
        boolean result = terrain.isInsideForbiddenArea(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void isOutsideForbiddenArea() {
        Vertex vertex = new Vertex(215, 215);

        boolean expected = false;
        boolean result = terrain.isInsideForbiddenArea(vertex);

        assertThat(result, equalTo(expected));
    }
}
