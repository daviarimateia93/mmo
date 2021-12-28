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
public class AnimateDiePacket implements NetworkPacket {

    private static final String ALIAS = "ANIMATE_DIE";

    private final UUID source;
    private final UUID killedBy;

    @Builder
    private AnimateDiePacket(@NonNull UUID source, @NonNull UUID killedBy) {
        this.source = source;
        this.killedBy = killedBy;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
