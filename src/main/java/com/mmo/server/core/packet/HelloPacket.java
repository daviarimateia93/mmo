package com.mmo.server.core.packet;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class HelloPacket implements NetworkPacket {

    public static final String ALIAS = "HELLO";

    private final UUID source;
    private final String userName;
    private final String userPassword;

    @Builder
    private HelloPacket(
            @NonNull UUID source,
            @NonNull String userName,
            @NonNull String userPassword) {

        this.source = source;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
