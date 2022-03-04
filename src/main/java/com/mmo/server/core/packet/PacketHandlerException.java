package com.mmo.server.core.packet;

import com.mmo.server.core.exception.RuntimeException;

public abstract class PacketHandlerException extends RuntimeException {

    private static final long serialVersionUID = 3973752540611483175L;

    public PacketHandlerException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public PacketHandlerException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
