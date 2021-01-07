package com.mmo.core.looper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class LooperTest {

    @Test
    @Timeout(value = 2100, unit = TimeUnit.MILLISECONDS)
    public void runWith15FpsBy2Seconds() {
        run(15, 2);
    }

    @Test
    @Timeout(value = 2100, unit = TimeUnit.MILLISECONDS)
    public void runWith30FpsBy2Seconds() {
        run(30, 2);
    }

    @Test
    @Timeout(value = 2100, unit = TimeUnit.MILLISECONDS)
    public void runWith60FpsBy2Seconds() {
        run(60, 2);
    }

    @Test
    @Timeout(value = 4100, unit = TimeUnit.MILLISECONDS)
    public void runWith120FpsBy4Seconds() {
        run(120, 4);
    }

    @Test
    @Timeout(value = 3100, unit = TimeUnit.MILLISECONDS)
    public void runWith240FpsBy3Seconds() {
        run(240, 3);
    }

    @Test
    @Timeout(value = 2100, unit = TimeUnit.MILLISECONDS)
    public void runWith480FpsBy2Seconds() {
        run(480, 2);
    }

    @Test
    @Timeout(value = 1100, unit = TimeUnit.MILLISECONDS)
    public void runWith960FpsBy1Second() {
        run(960, 1);
    }

    private void run(int fps, int durationInSeconds) {
        var updates = new ContextHandler();
        var renders = new ContextHandler();

        double lag = 1000 / fps;

        Looper looper = Looper.builder()
                .fps(fps)
                .updater(updates::increment)
                .renderer(renders::increment)
                .build();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.schedule(() -> looper.stop(), durationInSeconds, TimeUnit.SECONDS);

        looper.run();

        assertThat(updates.count, greaterThanOrEqualTo(fps * durationInSeconds));
        assertThat(updates.count.doubleValue(), lessThanOrEqualTo(fps * durationInSeconds * 1.10));
        assertThat(updates.context, notNullValue());
        assertThat(updates.context.getLag().doubleValue(), lessThanOrEqualTo(lag));
        assertThat(updates.context.getDesiredLag(), equalTo(lag));
        assertThat(updates.context.getMillisDuration(), notNullValue());
        assertThat(renders.count, greaterThan(updates.count));
        assertThat(renders.context, equalTo(updates.context));
    }

    private class ContextHandler {
        LooperContext context;
        Integer count = 0;

        int increment(LooperContext context) {
            this.context = context;
            return ++count;
        }
    }
}
