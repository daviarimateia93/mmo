package com.mmo.core.game;

import com.mmo.core.utils.RuntimeException;

public abstract class GameException extends RuntimeException {

    private static final long serialVersionUID = 5963259412229894080L;

    public GameException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public GameException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
