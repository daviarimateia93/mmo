package com.mmo.server.infrastructure.security;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.core.user.User;
import com.mmo.server.core.user.UserRepository;

public class AuthenticatorTest {

    private static PlayerRepository playerRepository;
    private static UserRepository userRepository;
    private static Authenticator authenticator;
    private static User user;
    private static Player player;

    @BeforeAll
    public static void setup() {
        playerRepository = mock(PlayerRepository.class);
        userRepository = mock(UserRepository.class);

        authenticator = Authenticator.builder()
                .playerRepository(playerRepository)
                .userRepository(userRepository)
                .build();

        user = User.builder()
                .id(UUID.randomUUID())
                .name("name")
                .password("password")
                .build();

        player = Player.builder()
                .userId(user.getId())
                .instanceId(UUID.randomUUID())
                .name("PlayerName-" + UUID.randomUUID())
                .position(Position.builder()
                        .x(50)
                        .z(10)
                        .build())
                .stats(Stats.builder()
                        .strength(10)
                        .dexterity(10)
                        .intelligence(10)
                        .concentration(10)
                        .sense(10)
                        .charm(10)
                        .build())
                .attributes(Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build())
                .build();

        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));
        when(playerRepository.find(player.getId())).thenReturn(Optional.of(player));
    }

    @Test
    public void authenticate() {
        boolean expected = true;
        boolean result = authenticator.authenticate(user.getName(), user.getPassword(), player.getId());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void authenticateWithWrongPassword() {
        boolean expected = false;
        boolean result = authenticator.authenticate(user.getName(), "wrong", player.getId());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void authenticateWithWrongPlayerId() {
        boolean expected = false;
        boolean result = authenticator.authenticate(user.getName(), user.getPassword(), UUID.randomUUID());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void authenticateWithWrongName() {
        boolean expected = false;
        boolean result = authenticator.authenticate("wrong", user.getPassword(), player.getId());

        assertThat(result, equalTo(expected));
    }
}
