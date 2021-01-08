package com.mmo.infrastructure.server;

import java.util.UUID;

public interface Packet {

    String getAlias();

    byte[] toBytes();

    default UUID getAliasAsUUID() {
        return UUID.nameUUIDFromBytes(getAlias().getBytes());
    }
}
