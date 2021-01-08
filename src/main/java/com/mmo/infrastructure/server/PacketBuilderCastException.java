package com.mmo.infrastructure.server;

public class PacketBuilderCastException extends PacketException {

    private static final long serialVersionUID = -5807763820125502977L;

    public PacketBuilderCastException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
