package com.mmo.infrastructure.server;

public class ClientReadException extends ClientException {

    private static final long serialVersionUID = -1911445511011728320L;

    public ClientReadException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
