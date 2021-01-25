package com.mmo.core.security;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class TokenDataTest {

    @Test
    public void parse() {
        UUID source = UUID.randomUUID();
        ZonedDateTime creation = ZonedDateTime.now();
        ZonedDateTime expiration = creation.plusDays(1);

        String token = String.format("%s@%s@%s", source, creation, expiration);

        TokenData expected = TokenData.builder()
                .source(source)
                .creation(creation)
                .expiration(expiration)
                .build();

        TokenData result = TokenData.parse(token);

        assertThat(expected, equalTo(result));
        assertThat(result.isExpired(), equalTo(false));
        assertThat(token, equalTo(result.getToken()));
    }

    @Test
    public void create() {
        UUID source = UUID.randomUUID();

        TokenData result = TokenData.create(source);

        TokenData expected = TokenData.builder()
                .source(source)
                .creation(result.getCreation())
                .expiration(result.getExpiration())
                .build();

        String token = String.format("%s@%s@%s", source, expected.getCreation(), expected.getExpiration());

        assertThat(expected, equalTo(result));
        assertThat(false, equalTo(result.isExpired()));
        assertThat(token, equalTo(result.getToken()));
    }
}
