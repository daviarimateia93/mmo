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
                .bottomLeftVertex(new Vertex(10, 2))
                .bottomRightVertex(new Vertex(20, 2))
                .topLeftVertex(new Vertex(10, 12))
                .topRightVertex(new Vertex(20, 12))
                .build();
    }

    @Test
    public void validateThrowsExceptionWhenInvalid() {
        assertThrows(InvalidRectangleException.class, () -> Rectangle.builder()
                .bottomLeftVertex(new Vertex(10, 2))
                .bottomRightVertex(new Vertex(20, 2))
                .topLeftVertex(new Vertex(10, 12))
                .topRightVertex(new Vertex(20, 13))
                .build());
    }

    @Test
    public void insetersectsCenter() {
        Vertex vertex = new Vertex(15, 6);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderBottomLeft() {
        Vertex vertex = new Vertex(10, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderBottomRight() {
        Vertex vertex = new Vertex(20, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderTopLeft() {
        Vertex vertex = new Vertex(10, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderTopRight() {
        Vertex vertex = new Vertex(20, 2);

        boolean expected = true;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsBottomLeft() {
        Vertex vertex = new Vertex(9, 2);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsBottomRight() {
        Vertex vertex = new Vertex(21, 2);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsTopLeft() {
        Vertex vertex = new Vertex(10, 1);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsTopRight() {
        Vertex vertex = new Vertex(20, 1);

        boolean expected = false;
        boolean result = rectangle.intersects(vertex);

        assertThat(result, equalTo(expected));
    }
}
