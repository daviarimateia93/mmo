package com.mmo.server.core.utils;

public abstract class RuntimeException extends java.lang.RuntimeException {

    private static final long serialVersionUID = -4532544621523985094L;

    public RuntimeException(String messageFormat, Object... arguments) {
        super(formatString(messageFormat, arguments));
    }

    public RuntimeException(Throwable throwable, String messageFormat, Object... arguments) {
        super(formatString(messageFormat, arguments), throwable);
    }

    public RuntimeException(Throwable throwable) {
        super(throwable);
    }

    private static String formatString(String messageFormat, Object... arguments) {
        return String.format(messageFormat, arguments);
    }
}
