package com.mmo.core.packet;

import java.util.UUID;

import com.mmo.core.player.Player;

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
    private final Player player;

    @Builder
    private PlayerPersistPacket(@NonNull UUID source, @NonNull Player player) {
        this.player = player;
        this.source = source;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
