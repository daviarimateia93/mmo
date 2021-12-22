package com.mmo.infrastructure.server;

public class PacketConverterCastException extends PacketException {

    private static final long serialVersionUID = -5807763820125502977L;

    public PacketConverterCastException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
