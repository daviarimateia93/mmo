package com.mmo.infrastructure.server.client;

public class ClientReadException extends ClientException {

    private static final long serialVersionUID = -1911445511011728320L;

    public ClientReadException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public ClientReadException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
