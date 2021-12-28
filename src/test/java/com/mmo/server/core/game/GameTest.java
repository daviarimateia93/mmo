package com.mmo.server.core.game;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.mmo.server.core.map.Map;

public class GameTest {

    private static Game game;

    @BeforeAll
    public static void setup() {
        game = Game.getInstance();
    }

    @Test
    public void getInstance() {
        assertThat(game, notNullValue());
    }

    @Test
    @Timeout(value = 5100, unit = TimeUnit.MILLISECONDS)
    public void runAndStop() throws InterruptedException {
        int duration = 5;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.schedule(() -> game.stop(), duration, TimeUnit.SECONDS);

        new Thread() {

            @Override
            public void run() {
                game.run(mock(Map.class));
            };

        }.start();

        Thread.sleep(2000);

        assertThat(game.isRunning(), equalTo(true));

        game.stop();

        assertThat(game.isRunning(), equalTo(false));
    }
}
