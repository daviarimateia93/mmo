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
                .build();
    }

    @Test
    public void validateThrowsExceptionWhenInvalid() {
        assertThrows(InvalidRectangleException.class, () -> Rectangle.builder()
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
                        .z(13L)
                        .build())
                .build());
    }

    @Test
    public void insetersectsCenter() {
        Position position = Position.builder()
                .x(15L)
                .y(15L)
                .z(6L)
                .build();

        boolean expected = true;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderBottomLeft() {
        Position position = Position.builder()
                .x(10L)
                .y(11L)
                .z(2L)
                .build();

        boolean expected = true;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderBottomRight() {
        Position position = Position.builder()
                .x(20L)
                .y(11L)
                .z(2L)
                .build();

        boolean expected = true;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderTopLeft() {
        Position position = Position.builder()
                .x(10L)
                .y(21L)
                .z(2L)
                .build();

        boolean expected = true;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void intersectsBorderTopRight() {
        Position position = Position.builder()
                .x(20L)
                .y(21L)
                .z(2L)
                .build();

        boolean expected = true;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsBottomLeft() {
        Position position = Position.builder()
                .x(9L)
                .y(11L)
                .z(2L)
                .build();

        boolean expected = false;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsBottomRight() {
        Position position = Position.builder()
                .x(21L)
                .y(11L)
                .z(2L)
                .build();

        boolean expected = false;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsTopLeft() {
        Position position = Position.builder()
                .x(10L)
                .y(22L)
                .z(2L)
                .build();

        boolean expected = false;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void doesNotIntersectsTopRight() {
        Position position = Position.builder()
                .x(20L)
                .y(22L)
                .z(2L)
                .build();

        boolean expected = false;
        boolean result = rectangle.intersects(position);

        assertThat(result, equalTo(expected));
    }
}
