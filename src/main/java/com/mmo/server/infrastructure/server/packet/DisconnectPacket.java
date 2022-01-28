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
    private final Long timestamp;

    @Builder
    private DisconnectPacket(@NonNull UUID source, @NonNull Long timestamp) {
        this.source = source;
        this.timestamp = timestamp;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
