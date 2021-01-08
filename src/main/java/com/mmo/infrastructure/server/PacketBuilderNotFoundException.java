package com.mmo.infrastructure.server;

public class PacketBuilderNotFoundException extends PacketException {

    private static final long serialVersionUID = -4857455772536305057L;

    public PacketBuilderNotFoundException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
