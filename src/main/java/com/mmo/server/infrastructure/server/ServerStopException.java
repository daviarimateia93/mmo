package com.mmo.server.infrastructure.server;

public class ServerStopException extends ServerException {

    private static final long serialVersionUID = 1609199622559238458L;

    public ServerStopException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
