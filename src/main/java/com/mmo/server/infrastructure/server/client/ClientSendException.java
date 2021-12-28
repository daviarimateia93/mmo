package com.mmo.server.infrastructure.server.client;

public class ClientSendException extends ClientException {

    private static final long serialVersionUID = -1403891901904129975L;

    public ClientSendException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }

    public ClientSendException(Throwable throwable) {
        super(throwable);
    }
}
