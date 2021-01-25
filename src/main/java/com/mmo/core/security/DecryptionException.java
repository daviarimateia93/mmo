package com.mmo.core.security;

public class DecryptionException extends CipherException {

    private static final long serialVersionUID = -8449505040943218392L;

    public DecryptionException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
