package com.mmo.server.infrastructure.server.client;

public class ClientDisconnectException extends ClientException {

    private static final long serialVersionUID = 3966866326354967061L;

    public ClientDisconnectException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
