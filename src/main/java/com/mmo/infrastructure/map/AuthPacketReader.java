package com.mmo.infrastructure.map;

import java.util.Objects;

public class AuthPacketReader {

    private static AuthPacketReader instance;

    public static AuthPacketReader getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AuthPacketReader();
        }

        return instance;
    }
}
