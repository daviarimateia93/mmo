package com.mmo.server.core.looper;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class LooperContextBag implements LooperContext {

    private Long tick;
    private Long lag;
    private Double desiredLag;

    protected LooperContextBag() {

    }
}
