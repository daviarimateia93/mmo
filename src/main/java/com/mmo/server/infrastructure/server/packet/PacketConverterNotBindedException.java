package com.mmo.server.infrastructure.server.packet;

public class PacketConverterNotBindedException extends PacketException {

    private static final long serialVersionUID = -4857455772536305057L;

    public PacketConverterNotBindedException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
