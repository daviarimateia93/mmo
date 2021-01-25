package com.mmo.infrastructure.server;

import java.util.UUID;

public interface Packet {

    String getAlias();

    default UUID getAliasAsUUID() {
        return UUID.nameUUIDFromBytes(getAlias().getBytes());
    }

    UUID getSource();

    byte[] toBytes();
}
