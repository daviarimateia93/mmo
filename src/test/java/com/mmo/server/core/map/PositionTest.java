package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void incrementX() {
        Position position = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        position.incrementX(10);

        assertThat(position.getX(), equalTo(20L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementZ() {
        Position position = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        position.incrementZ(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(20L));
    }

    @Test
    public void incrementY() {
        Position position = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        position.incrementY(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(20L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementX() {
        Position position = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        position.decrementX(10);

        assertThat(position.getX(), equalTo(0L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementY() {
        Position position = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        position.decrementY(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(0L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementZ() {
        Position position = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        position.decrementZ(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(0L));
    }

    @Test
    public void isNearby() {
        Position basePosition = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        Position testingPosition = Position.builder()
                .x(20L)
                .y(15L)
                .z(15L)
                .build();

        boolean expected = true;
        boolean result = basePosition.isNearby(testingPosition, 10);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void isNotNearby() {
        Position basePosition = Position.builder()
                .x(10L)
                .y(10L)
                .z(10L)
                .build();

        Position testingPosition = Position.builder()
                .x(20L)
                .y(15L)
                .z(15L)
                .build();

        boolean expected = false;
        boolean result = basePosition.isNearby(testingPosition, 9);

        assertThat(result, equalTo(expected));
    }
}
