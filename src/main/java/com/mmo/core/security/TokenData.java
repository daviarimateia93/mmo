package com.mmo.core.security;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TokenData {

    private static final int TOKEN_EXPIRATION_MINUTES = 15;

    private final UUID source;
    private final ZonedDateTime creation;
    private final ZonedDateTime expiration;

    @Builder(access = AccessLevel.PROTECTED)
    private TokenData(
            @NonNull UUID source,
            @NonNull ZonedDateTime creation,
            @NonNull ZonedDateTime expiration) {

        this.source = source;
        this.creation = creation;
        this.expiration = expiration;
    }

    public boolean isExpired() {
        return expiration.isBefore(ZonedDateTime.now());
    }

    public String getToken() {
        return String.format("%s@%s@%s", source, creation, expiration);
    }

    public static TokenData parse(String token) {
        String[] parts = token.split("\\@");

        return TokenData.builder()
                .source(UUID.fromString(parts[0]))
                .creation(ZonedDateTime.parse(parts[1]))
                .expiration(ZonedDateTime.parse(parts[2]))
                .build();
    }

    public static TokenData create(UUID source) {
        ZonedDateTime now = ZonedDateTime.now();

        return TokenData.builder()
                .source(source)
                .creation(now)
                .expiration(now.plusMinutes(TOKEN_EXPIRATION_MINUTES))
                .build();
    }
}
