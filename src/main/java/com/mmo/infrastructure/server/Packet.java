package com.mmo.infrastructure.server;

import java.util.UUID;

public interface Packet {

    UUID getId();

    String getAlias();

    byte[] toBytes();

    default UUID getAliasAsUUID() {
        return UUID.nameUUIDFromBytes(getAlias().getBytes());
    }
}
