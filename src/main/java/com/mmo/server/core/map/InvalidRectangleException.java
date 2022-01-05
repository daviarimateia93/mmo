package com.mmo.server.core.map;

public class InvalidRectangleException extends MapException {

    private static final long serialVersionUID = 3921162486248260577L;

    public InvalidRectangleException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
