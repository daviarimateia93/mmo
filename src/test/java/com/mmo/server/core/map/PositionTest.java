package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.game.GameRunnerMapMocker;

public class PositionTest {

    private static Map map;

    @BeforeAll
    private static void setup() {
        map = GameRunnerMapMocker.run();
    }

    @AfterAll
    private static void clear() {
        GameRunnerMapMocker.stop();
    }

    @AfterEach
    private void clearEach() {
        when(map.getTerrain().isInsideForbiddenArea(anyLong(), anyLong(), anyLong())).thenReturn(false);
    }

    @Test
    public void incrementX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementX(10);

        assertThat(position.getX(), equalTo(20L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementOneX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementX();

        assertThat(position.getX(), equalTo(11L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementXWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(15, 10, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementX(10);

        assertThat(position.getX(), equalTo(14L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementY(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(20L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementOneY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementY();

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(11L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementYWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 15, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementY(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(14L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void incrementZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementZ(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(20L));
    }

    @Test
    public void incrementOneZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementZ();

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(11L));
    }

    @Test
    public void incrementZWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 10, 15)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementZ(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(14L));
    }

    @Test
    public void decrementX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementX(10);

        assertThat(position.getX(), equalTo(0L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementOneX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementX();

        assertThat(position.getX(), equalTo(9L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementXWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(5, 10, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementX(10);

        assertThat(position.getX(), equalTo(6L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementY(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(0L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementOneY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementY();

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(9L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementYWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 5, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementY(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(6L));
        assertThat(position.getZ(), equalTo(10L));
    }

    @Test
    public void decrementZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementZ(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(0L));
    }

    @Test
    public void decrementOneZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementZ();

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(9L));
    }

    @Test
    public void decrementZWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 10, 5)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementZ(10);

        assertThat(position.getX(), equalTo(10L));
        assertThat(position.getY(), equalTo(10L));
        assertThat(position.getZ(), equalTo(6L));
    }
}
