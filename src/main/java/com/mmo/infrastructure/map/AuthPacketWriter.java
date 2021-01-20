package com.mmo.infrastructure.map;

import java.util.Objects;

public class AuthPacketWriter {

    private static AuthPacketWriter instance;

    public static AuthPacketWriter getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AuthPacketWriter();
        }

        return instance;
    }
}
