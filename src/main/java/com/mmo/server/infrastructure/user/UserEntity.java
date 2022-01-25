package com.mmo.server.infrastructure.user;

import java.util.UUID;

import com.mmo.server.core.user.User;

import lombok.Data;

@Data
public class UserEntity {

    private UUID id;
    private String name;
    private String password;

    public User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();
    }

    public static UserEntity of(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());

        return entity;
    }
}
