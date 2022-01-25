package com.mmo.server.infrastructure.security;

import java.util.UUID;

import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.core.user.User;
import com.mmo.server.core.user.UserRepository;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Authenticator {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

    @Builder
    private Authenticator(@NonNull UserRepository userRepository, @NonNull PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    public boolean authenticate(String userName, String userPassword, UUID playerId) {
        return userRepository
                .findByName(userName)
                .filter(user -> userPasswordMatches(user, userPassword))
                .filter(user -> playerBelongsToUser(user, playerId))
                .map(user -> true)
                .orElse(false);

    }

    private boolean userPasswordMatches(User user, String userPassword) {
        return user.getPassword().equalsIgnoreCase(userPassword);
    }

    private boolean playerBelongsToUser(User user, UUID playerId) {
        return playerRepository.find(playerId)
                .filter(player -> player.getUserId().equals(user.getId()))
                .map(player -> true)
                .orElse(false);
    }
}
