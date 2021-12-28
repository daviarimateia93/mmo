package com.mmo.server.infrastructure.server;

public class ServerStartException extends ServerException {

    private static final long serialVersionUID = -8382104388943632048L;

    public ServerStartException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
