package com.mmo.server.infrastructure.config;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConfigProviderTest {

    private static ConfigProvider configProvider;

    @BeforeAll
    public static void setup() {
        configProvider = ConfigProvider.getInstance();
    }

    @Test
    public void getFromEnvironment() {
        String result = configProvider.getString("JAVA_HOME");

        assertThat(result, notNullValue());
    }

    @Test
    public void getString() {
        String expected = "abc";
        String result = configProvider.getString("config.provider.test.string");

        assertThat(result, is(expected));
    }

    @Test
    public void getStringThrowsExceptionWhenNotFound() {
        assertThrows(ConfigNotFoundException.class, () -> configProvider.getString("unknown"));
    }

    @Test
    public void getUUID() {
        UUID expected = UUID.fromString("c316c6cf-27f1-464d-bcb9-3480a73833fe");
        UUID result = configProvider.getUUID("config.provider.test.uuid");

        assertThat(result, is(expected));
    }

    @Test
    public void getUUIDThrowsExceptionWhenNotFound() {
        assertThrows(ConfigNotFoundException.class, () -> configProvider.getUUID("unknown"));
    }

    @Test
    public void getLong() {
        Long expected = 67L;
        Long result = configProvider.getLong("config.provider.test.long");

        assertThat(result, is(expected));
    }

    @Test
    public void getLongThrowsExceptionWhenNotFound() {
        assertThrows(ConfigNotFoundException.class, () -> configProvider.getLong("unknown"));
    }

    @Test
    public void getInt() {
        Integer expected = 23;
        Integer result = configProvider.getInt("config.provider.test.int");

        assertThat(result, is(expected));
    }

    @Test
    public void getIntThrowsExceptionWhenNotFound() {
        assertThrows(ConfigNotFoundException.class, () -> configProvider.getInt("unknown"));
    }

    @Test
    public void getBoolean() {
        boolean expected = true;
        boolean result = configProvider.getBoolean("config.provider.test.boolean");

        assertThat(result, is(expected));
    }

    @Test
    public void getBooleanThrowsExceptionWhenNotFound() {
        assertThrows(ConfigNotFoundException.class, () -> configProvider.getBoolean("unknown"));
    }
}
