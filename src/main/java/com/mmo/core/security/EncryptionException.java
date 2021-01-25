package com.mmo.core.security;

public class EncryptionException extends CipherException {

    private static final long serialVersionUID = -2299950766230240050L;

    public EncryptionException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
