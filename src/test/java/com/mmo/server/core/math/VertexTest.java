package com.mmo.server.core.math;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class VertexTest {

    @Test
    public void getDistance() {
        Vertex basePosition = new Vertex(10, 10);
        Vertex testingPosition = new Vertex(20, 15);

        float expected = 125;
        float result = basePosition.getDistance(testingPosition);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void isNearby() {
        Vertex basePosition = new Vertex(10, 10);
        Vertex testingPosition = new Vertex(20, 15);

        boolean expected = true;
        boolean result = basePosition.isNearby(testingPosition, 10);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void isNotNearby() {
        Vertex basePosition = new Vertex(10, 10);
        Vertex testingPosition = new Vertex(20, 15);

        boolean expected = false;
        boolean result = basePosition.isNearby(testingPosition, 9);

        assertThat(result, equalTo(expected));
    }
}
