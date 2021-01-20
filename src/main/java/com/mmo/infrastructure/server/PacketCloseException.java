package com.mmo.infrastructure.server;

public class PacketCloseException extends PacketException {

    private static final long serialVersionUID = 628542808139704688L;

    public PacketCloseException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
