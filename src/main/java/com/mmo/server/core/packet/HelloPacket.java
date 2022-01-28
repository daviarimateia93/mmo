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
    private final Long timestamp;
    private final String userName;
    private final String userPassword;

    @Builder
    private HelloPacket(
            @NonNull UUID source,
            @NonNull Long timestamp,
            @NonNull String userName,
            @NonNull String userPassword) {

        this.source = source;
        this.timestamp = timestamp;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }
}
