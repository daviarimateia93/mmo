package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RectangleTest {

    private static Rectangle rectangle;

    @BeforeAll
    public static void setup() {
        rectangle = Rectangle.builder()
                .bottomLeftVertex(new Vertex(10, 11, 2))
                .bottomRightVertex(new Vertex(20, 11, 2))
                .topLeftVertex(new Vertex(10, 21, 12))
                .topRightVertex(new Vertex(20, 21, 12))
                .build();
    }

    @Test
    public void validateThrowsExceptionWhenInvalid() {
        assertThrows(InvalidRectangleException.class, () -> Rectangle.builder()
                .bottomLeftVertex(new Vertex(10, 11, 2))
                .bottomRightVertex(new Vertex(20, 11, 2))
                .topLeftVertex(new Vertex(10, 21, 12))
                .topRightVertex(new Vertex(20, 21, 13))
                .build());
    }

    @Test
    public void insetersectsCenter() {
        Vertex vertex = new Vertex(15, 15, 6);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderBottomLeft() {
        Vertex vertex = new Vertex(10, 11, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderBottomRight() {
        Vertex vertex = new Vertex(20, 11, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderTopLeft() {
        Vertex vertex = new Vertex(10, 21, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderTopRight() {
        Vertex vertex = new Vertex(20, 21, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsBottomLeft() {
        Vertex vertex = new Vertex(9, 11, 2);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsBottomRight() {
        Vertex vertex = new Vertex(21, 11, 2);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsTopLeft() {
        Vertex vertex = new Vertex(10, 22, 2);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsTopRight() {
        Vertex vertex = new Vertex(20, 22, 2);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }
}
