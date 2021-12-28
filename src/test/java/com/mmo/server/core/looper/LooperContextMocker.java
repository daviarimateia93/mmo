package com.mmo.server.core.looper;

import static org.mockito.Mockito.*;

public class LooperContextMocker {

    public static void update(LooperUpdater updater, long millis) {
        try {
            Thread.sleep(millis);
            long now = System.currentTimeMillis();
            LooperContext context = mock(LooperContext.class);
            when(context.getTick()).thenReturn(now);
            when(context.getLag()).thenReturn(16L);
            when(context.getDesiredLag()).thenReturn(16.0);
            when(context.getMillisDuration()).thenReturn(System.currentTimeMillis() - now);
            updater.update(context);
        } catch (InterruptedException exception) {

        }
    }
}
