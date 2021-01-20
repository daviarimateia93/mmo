package com.mmo.core.map;

import com.mmo.core.RuntimeException;

public abstract class MapException extends RuntimeException {

    private static final long serialVersionUID = 2056908251962730981L;

    public MapException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public MapException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
