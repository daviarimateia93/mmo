package com.mmo.core.looper;

public class LooperWaitingException extends LooperException {

    private static final long serialVersionUID = 4229927300047871562L;

    public LooperWaitingException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
