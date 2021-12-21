package com.mmo.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class ConfigProvider {

    private static final String ENVIRONMENT_KEY_PROFILE = "profile";
    private static final String PROFILE_DEFAULT = "default";
    private static final String PROPERTY_FILE_NAME = "application";

    private static ConfigProvider instance;

    private final Properties properties;

    private ConfigProvider() {
        properties = new Properties();
        loadProperties();
    }

    public static ConfigProvider getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ConfigProvider();
        }

        return instance;
    }

    public String getString(String name) {
        return getFromEnvironment(name)
                .orElseGet(() -> getFromProperty(name)
                        .orElseThrow(() -> new ConfigNotFoundException("Config %s not found", name)));
    }

    public Long getLong(String name) {
        return Long.valueOf(getString(name));
    }

    public Integer getInteger(String name) {
        return Integer.valueOf(getString(name));
    }

    public Boolean getBoolean(String name) {
        return Boolean.valueOf(getString(name));
    }

    private boolean isDefaultProfile() {
        return PROFILE_DEFAULT.equalsIgnoreCase(getCurrentProfile());
    }

    private String getCurrentProfile() {
        return getFromEnvironment(ENVIRONMENT_KEY_PROFILE).orElse(PROFILE_DEFAULT);
    }

    private void loadProperties() {
        String fileNameWithExtension = getPropertyFileName() + ".properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileNameWithExtension);

        try {
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new ConfigPropertiesLoadException(exception, "Failed to load properties file");
        }
    }

    private Optional<String> getFromEnvironment(String name) {
        return Optional.ofNullable(System.getenv(name));
    }

    private Optional<String> getFromProperty(String name) {
        return Optional.ofNullable(properties.getProperty(name));
    }

    private String getPropertyFileName() {
        if (isDefaultProfile()) {
            return PROPERTY_FILE_NAME;
        }

        return PROPERTY_FILE_NAME + "_" + getCurrentProfile();
    }
}
