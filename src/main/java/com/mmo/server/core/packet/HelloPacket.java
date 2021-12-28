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
public class HelloPacket implements NetworkPacket {

    public static final String ALIAS = "HELLO";

    private final UUID source;

    @Builder
    private HelloPacket(@NonNull UUID source) {
        this.source = source;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
