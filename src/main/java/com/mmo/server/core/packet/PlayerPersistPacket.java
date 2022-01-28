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
public class PlayerPersistPacket implements PersistencePacket {

    public static final String ALIAS = "PLAYER_PERSIST";

    private final UUID source;
    private final Long timestamp;
    private final Player player;

    @Builder
    private PlayerPersistPacket(
            @NonNull UUID source,
            @NonNull Long timestamp,
            @NonNull Player player) {

        this.source = source;
        this.timestamp = timestamp;
        this.player = player;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
