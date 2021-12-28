package com.mmo.server.infrastructure.config;

public class ConfigNotFoundException extends ConfigException {

    private static final long serialVersionUID = -2303277648567974365L;

    public ConfigNotFoundException(String messageFormat, Object... arguments) {
        super(messageFormat, arguments);
    }
}
