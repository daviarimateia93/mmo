package com.mmo.infrastructure.server.client;

import com.mmo.core.utils.RuntimeException;

public abstract class ClientException extends RuntimeException {

    private static final long serialVersionUID = 7131609453166100592L;

    public ClientException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public ClientException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
