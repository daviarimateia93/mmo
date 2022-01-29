package com.mmo.server.infrastructure.user;

import java.util.UUID;

import com.mmo.server.core.user.User;

import lombok.Data;

@Data
public class UserDTO {

    private UUID id;
    private String name;
    private String password;

    public static UserDTO of(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());

        return dto;
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();
    }
}
