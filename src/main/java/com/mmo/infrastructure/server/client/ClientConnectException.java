package com.mmo.infrastructure.server.client;

public class ClientConnectException extends ClientException {

    private static final long serialVersionUID = 601225599534698333L;

    public ClientConnectException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
