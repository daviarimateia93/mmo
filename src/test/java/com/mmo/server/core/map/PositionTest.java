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

        Position expected = Position.builder()
                .x(20)
                .y(10)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementOneX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementX();

        Position expected = Position.builder()
                .x(11)
                .y(10)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementXWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(15, 10, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        boolean result = position.incrementX(10);

        assertThat(result, equalTo(false));

        Position expected = Position.builder()
                .x(14)
                .y(10)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementY(10);

        Position expected = Position.builder()
                .x(10)
                .y(20)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementOneY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementY();

        Position expected = Position.builder()
                .x(10)
                .y(11)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementYWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 15, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        boolean result = position.incrementY(10);

        assertThat(result, equalTo(false));

        Position expected = Position.builder()
                .x(10)
                .y(14)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementZ(10);

        Position expected = Position.builder()
                .x(10)
                .y(10)
                .z(20)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementOneZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.incrementZ();

        Position expected = Position.builder()
                .x(10)
                .y(10)
                .z(11)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void incrementZWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 10, 15)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        boolean result = position.incrementZ(10);

        assertThat(result, equalTo(false));

        Position expected = Position.builder()
                .x(10)
                .y(10)
                .z(14)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementX(10);

        Position expected = Position.builder()
                .x(0)
                .y(10)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementOneX() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementX();

        Position expected = Position.builder()
                .x(9)
                .y(10)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementXWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(5, 10, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        boolean result = position.decrementX(10);

        assertThat(result, equalTo(false));

        Position expected = Position.builder()
                .x(6)
                .y(10)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementY(10);

        Position expected = Position.builder()
                .x(10)
                .y(0)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementOneY() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementY();

        Position expected = Position.builder()
                .x(10)
                .y(9)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementYWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 5, 10)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        boolean result = position.decrementY(10);

        assertThat(result, equalTo(false));

        Position expected = Position.builder()
                .x(10)
                .y(6)
                .z(10)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementZ(10);

        Position expected = Position.builder()
                .x(10)
                .y(10)
                .z(0)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementOneZ() {
        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        position.decrementZ();

        Position expected = Position.builder()
                .x(10)
                .y(10)
                .z(9)
                .build();

        assertThat(position, equalTo(expected));
    }

    @Test
    public void decrementZWhenCollision() {
        when(map.getTerrain().isInsideForbiddenArea(10, 10, 5)).thenReturn(true);

        Position position = Position.builder()
                .x(10)
                .y(10)
                .z(10)
                .build();

        boolean result = position.decrementZ(10);

        assertThat(result, equalTo(false));

        Position expected = Position.builder()
                .x(10)
                .y(10)
                .z(6)
                .build();

        assertThat(position, equalTo(expected));
    }
}
