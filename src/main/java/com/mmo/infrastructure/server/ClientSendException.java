package com.mmo.infrastructure.server;

public class ClientSendException extends ClientException {

    private static final long serialVersionUID = -1403891901904129975L;

    public ClientSendException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
