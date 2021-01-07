package com.mmo.core.game;

public class GameAlreadyRunningException extends GameException {

    private static final long serialVersionUID = 4205315463920114094L;

    public GameAlreadyRunningException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
