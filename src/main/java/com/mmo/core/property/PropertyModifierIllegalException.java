package com.mmo.core.property;

public class PropertyModifierIllegalException extends PropertyException {

    private static final long serialVersionUID = 6074848969038356585L;

    public PropertyModifierIllegalException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
