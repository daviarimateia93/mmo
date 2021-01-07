package com.mmo.infrastructure.map;

import com.mmo.core.RuntimeException;

public abstract class MapServerException extends RuntimeException {

    private static final long serialVersionUID = -2830072185814533955L;

    public MapServerException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }

    public MapServerException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
