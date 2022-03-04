package com.mmo.server.core.property;

import com.mmo.server.core.exception.RuntimeException;

public abstract class PropertyException extends RuntimeException {

    private static final long serialVersionUID = -5825865041705888874L;

    public PropertyException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public PropertyException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
