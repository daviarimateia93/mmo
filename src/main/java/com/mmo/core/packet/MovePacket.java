package com.mmo.core.packet;

import java.util.UUID;

import com.mmo.core.map.Position;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MovePacket implements NetworkPacket {

    public static final String ALIAS = "MOVE";

    private final UUID source;
    private final Position target;

    @Builder
    private MovePacket(@NonNull UUID source, @NonNull Position target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
