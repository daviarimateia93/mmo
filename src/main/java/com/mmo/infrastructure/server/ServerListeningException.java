package com.mmo.infrastructure.server;

public class ServerListeningException extends ServerException {

    private static final long serialVersionUID = 5927598150805603931L;

    public ServerListeningException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
