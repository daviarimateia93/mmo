package com.mmo.infrastructure.server;

public class PacketCastException extends PacketException {

    private static final long serialVersionUID = -5807763820125502977L;

    public PacketCastException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
