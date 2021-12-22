package com.mmo.infrastructure.security.aes;

import com.mmo.core.utils.RuntimeException;

public abstract class CipherException extends RuntimeException {

    private static final long serialVersionUID = -1119006880282972149L;

    public CipherException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
