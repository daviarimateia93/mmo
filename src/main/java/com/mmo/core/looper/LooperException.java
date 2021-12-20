package com.mmo.core.looper;

import com.mmo.core.utils.RuntimeException;

public abstract class LooperException extends RuntimeException {

    private static final long serialVersionUID = -1353260048190730191L;

    public LooperException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public LooperException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
