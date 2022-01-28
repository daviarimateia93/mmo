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

    public static final String ALIAS = "ANIMATE_DIE";

    private final UUID source;
    private final Long timestamp;
    private final UUID killedBy;

    @Builder
    private AnimateDiePacket(
            @NonNull UUID source,
            @NonNull Long timestamp,
            @NonNull UUID killedBy) {

        this.source = source;
        this.timestamp = timestamp;
        this.killedBy = killedBy;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
