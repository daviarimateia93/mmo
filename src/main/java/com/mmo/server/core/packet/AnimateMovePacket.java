package com.mmo.server.core.packet;

import java.util.UUID;

import com.mmo.server.core.map.Position;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AnimateMovePacket implements NetworkPacket {

    public static final String ALIAS = "ANIMATE_MOVE";

    private final UUID source;
    private final Long timestamp;
    private final Position target;

    @Builder
    private AnimateMovePacket(
            @NonNull UUID source,
            @NonNull Long timestamp,
            @NonNull Position target) {

        this.source = source;
        this.timestamp = timestamp;
        this.target = target;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
