package com.mmo.server.core.packet;

public class PacketHandlerNotBindedException extends PacketHandlerException {

    private static final long serialVersionUID = -832967441577651014L;

    public PacketHandlerNotBindedException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
