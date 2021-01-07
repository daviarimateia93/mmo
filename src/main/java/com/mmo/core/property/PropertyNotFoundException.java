package com.mmo.core.property;

public class PropertyNotFoundException extends PropertyException {

    private static final long serialVersionUID = -3223779855788831263L;

    public PropertyNotFoundException(String propertyName) {
        super("Property %s not found", propertyName);
    }
}
