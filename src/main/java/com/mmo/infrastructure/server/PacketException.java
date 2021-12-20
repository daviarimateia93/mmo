package com.mmo.infrastructure.server;

import com.mmo.core.utils.RuntimeException;

public abstract class PacketException extends RuntimeException {

    private static final long serialVersionUID = 3989064985294348732L;

    public PacketException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public PacketException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
