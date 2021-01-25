package com.mmo.core.security;

public class CipherInitializationException extends CipherException {

    private static final long serialVersionUID = 8377003887130722827L;

    public CipherInitializationException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
