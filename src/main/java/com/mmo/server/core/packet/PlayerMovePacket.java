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
public class PlayerMovePacket implements NetworkPacket {

    public static final String ALIAS = "PLAYER_MOVE";

    private final UUID source;
    private final Position target;

    @Builder
    private PlayerMovePacket(@NonNull UUID source, @NonNull Position target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
