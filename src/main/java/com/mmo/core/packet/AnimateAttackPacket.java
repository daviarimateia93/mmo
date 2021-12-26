package com.mmo.core.packet;

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
    private final UUID target;

    @Builder
    private AnimateAttackPacket(@NonNull UUID source, @NonNull UUID target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
