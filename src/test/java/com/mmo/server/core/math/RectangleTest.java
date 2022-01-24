package com.mmo.server.core.math;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.map.InvalidRectangleException;

public class RectangleTest {

    private static Rectangle rectangle;
    private static Rectangle complexRectangle;

    @BeforeAll
    public static void setup() {
        rectangle = Rectangle.builder()
                .bottomLeftVertex(new Vertex(10, 2))
                .bottomRightVertex(new Vertex(20, 2))
                .topLeftVertex(new Vertex(10, 12))
                .topRightVertex(new Vertex(20, 12))
                .build();

        complexRectangle = Rectangle.builder()
                .bottomLeftVertex(new Vertex(-8, -7))
                .bottomRightVertex(new Vertex(7, -7))
                .topLeftVertex(new Vertex(-8, 5))
                .topRightVertex(new Vertex(7, 5))
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

    @Test
    public void getWidth() {
        int expected = 15;
        int result = complexRectangle.getWidth();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getDepth() {
        int expected = 12;
        int result = complexRectangle.getDepth();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getCenter() {
        Vertex expected = new Vertex(-1, -1);
        Vertex result = complexRectangle.getCenter();

        assertThat(result, equalTo(expected));
    }
}
