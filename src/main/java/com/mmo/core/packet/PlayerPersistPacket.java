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
public class PlayerPersistPacket implements PersistencePacket {

    public static final String ALIAS = "PLAYER_PERSIST";

    private final UUID source;

    @Builder
    private PlayerPersistPacket(@NonNull UUID source) {
        this.source = source;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
