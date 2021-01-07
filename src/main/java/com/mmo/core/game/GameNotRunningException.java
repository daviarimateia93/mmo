package com.mmo.core.game;

public class GameNotRunningException extends GameException {

    private static final long serialVersionUID = -6897440764011691878L;

    public GameNotRunningException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
