package com.mmo.server.core.packet;

import java.util.UUID;

import com.mmo.server.core.player.Player;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PlayerUpdatePacket implements NetworkPacket {

    public static final String ALIAS = "PLAYER_UPDATE";

    private final UUID source;
    private final Player player;

    @Builder
    private PlayerUpdatePacket(@NonNull UUID source, @NonNull Player player) {
        this.source = source;
        this.player = player;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
