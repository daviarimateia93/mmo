package com.mmo.server.core.looper;

import java.util.Optional;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Looper implements Runnable {

    private final Integer fps;
    private final LooperRenderer renderer;
    private final LooperUpdater updater;
    private boolean running;
    private Long lastTick;
    private LooperContextBag context;

    @Builder
    private Looper(
            @NonNull Integer fps,
            @NonNull LooperRenderer renderer,
            @NonNull LooperUpdater updater) {

        this.fps = fps;
        this.renderer = renderer;
        this.updater = updater;

        context = new LooperContextBag();
    }

    public Optional<Long> getLastTick() {
        return Optional.ofNullable(lastTick);
    }

    @Override
    public void run() {
        if (running) {
            return;
        }

        double msPerUpdate = 1000 / fps;
        long lag = 0;
        lastTick = getNewTick();
        context.setDesiredLag(msPerUpdate);
        running = true;

        while (running) {
            long newTick = getNewTick();
            long elapsed = newTick - lastTick;

            lag += elapsed;
            lastTick = newTick;
            context.setTick(newTick);
            context.setLag(lag);

            while (lag >= msPerUpdate) {
                updater.update(context);
                lag -= msPerUpdate;
            }

            renderer.render(context);
        }
    }

    public void stop() {
        running = false;
    }

    private static long getNewTick() {
        return System.currentTimeMillis();
    }

    @SuppressWarnings("unused")
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException exception) {
            throw new LooperWaitingException(exception, "Thread sleep was interrupted");
        }
    }
}
