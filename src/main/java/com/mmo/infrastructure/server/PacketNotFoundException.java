package com.mmo.infrastructure.server;

public class PacketNotFoundException extends PacketException {

    private static final long serialVersionUID = -4857455772536305057L;

    public PacketNotFoundException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
