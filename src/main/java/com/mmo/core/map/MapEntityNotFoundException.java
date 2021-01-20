package com.mmo.core.map;

public class MapEntityNotFoundException extends MapException {

    private static final long serialVersionUID = 3885614904306733067L;

    public MapEntityNotFoundException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
