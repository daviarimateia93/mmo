package com.mmo.server.core.packet;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AnimateAttackPacket implements NetworkPacket {

    public static final String ALIAS = "ANIMATE_ATTACK";

    private final UUID source;
    private final Long timestamp;
    private final UUID target;

    @Builder
    private AnimateAttackPacket(
            @NonNull UUID source,
            @NonNull Long timestamp,
            @NonNull UUID target) {
        
        this.source = source;
        this.timestamp = timestamp;
        this.target = target;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
