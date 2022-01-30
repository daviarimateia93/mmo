package com.mmo.server.core.looper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public interface LooperContext {

    Long getTick();

    Long getLag();

    Double getDesiredLag();

    default OffsetDateTime getTickDateTime() {
        Instant instant = Instant.ofEpochMilli(getTick());
        ZoneId zoneId = ZoneId.systemDefault();

        return OffsetDateTime.ofInstant(instant, zoneId);
    }

    default Long getMillisDuration() {
        return System.currentTimeMillis() - getTick();
    }
}
