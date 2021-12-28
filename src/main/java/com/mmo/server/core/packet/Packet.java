package com.mmo.server.core.packet;

import java.util.UUID;

public interface Packet {

    String getAlias();

    default UUID getAliasAsUUID() {
        return UUID.nameUUIDFromBytes(getAlias().getBytes());
    }

    UUID getSource();
}
