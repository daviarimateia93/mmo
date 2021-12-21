package com.mmo.infrastructure.config;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

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
    public void getInteger() {
        Integer expected = 23;
        Integer result = configProvider.getInteger("config.provider.test.integer");

        assertThat(result, is(expected));
    }

    @Test
    public void getIntegerThrowsExceptionWhenNotFound() {
        assertThrows(ConfigNotFoundException.class, () -> configProvider.getInteger("unknown"));
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
