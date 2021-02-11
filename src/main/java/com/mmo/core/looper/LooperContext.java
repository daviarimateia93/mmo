package com.mmo.core.looper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class LooperContext {

    private Long tick;
    private Long lag;
    private Double desiredLag;

    protected LooperContext() {

    }

    public OffsetDateTime getTickDateTime() {
        Instant instant = Instant.ofEpochMilli(tick);
        ZoneId zoneId = ZoneId.systemDefault();

        return OffsetDateTime.ofInstant(instant, zoneId);
    }

    public Long getMillisDuration() {
        return System.currentTimeMillis() - tick;
    }
}
