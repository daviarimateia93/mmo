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
public class AttackPacket implements Packet {

    public static final String ALIAS = "ATTACK";

    private final UUID source;
    private final UUID target;

    @Builder
    private AttackPacket(@NonNull UUID source, @NonNull UUID target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
