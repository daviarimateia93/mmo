package com.mmo.infrastructure.server;

public class PacketBuilderNotBindedException extends PacketException {

    private static final long serialVersionUID = -4857455772536305057L;

    public PacketBuilderNotBindedException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
