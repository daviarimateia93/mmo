package com.mmo.server.core.looper;

import lombok.Data;

@Data
public class LooperContextBag implements LooperContext {

    private Long tick;
    private Long lag;
    private Double desiredLag;
}
