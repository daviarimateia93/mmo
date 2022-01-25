package com.mmo.server.core.user;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class User {

    private final UUID id;
    private final String name;
    private final String password;

    @Builder
    private User(
            @NonNull UUID id,
            @NonNull String name,
            @NonNull String password) {

        this.id = id;
        this.name = name;
        this.password = password;
    }
}
