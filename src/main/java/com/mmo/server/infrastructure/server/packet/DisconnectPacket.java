package com.mmo.server.infrastructure.server.packet;

import java.util.UUID;

import com.mmo.server.core.packet.NetworkPacket;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class DisconnectPacket implements NetworkPacket {

    public static final String ALIAS = "DISCONNECT";

    private final UUID source;

    @Builder
    private DisconnectPacket(@NonNull UUID source) {
        this.source = source;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
