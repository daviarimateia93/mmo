package com.mmo.server.infrastructure.config;

public class ConfigPropertiesLoadException extends ConfigException {

    private static final long serialVersionUID = 6325393108316205513L;

    public ConfigPropertiesLoadException(Throwable throwable, String messageFormat, Object... arguments) {
        super(throwable, messageFormat, arguments);
    }
}
