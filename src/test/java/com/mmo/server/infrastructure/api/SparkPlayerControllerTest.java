package com.mmo.server.infrastructure.api;

import static com.mashape.unirest.http.Unirest.get;
import static com.mmo.server.infrastructure.api.Spark.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.player.PlayerEntity;
import com.mmo.server.infrastructure.security.Authenticator;

public class SparkPlayerControllerTest {

    private static PlayerRepository repository;
    private static Authenticator authenticator;

    @BeforeAll
    public static void setup() {
        repository = mock(PlayerRepository.class);
        authenticator = mock(Authenticator.class);

        SparkPlayerController.builder()
                .repository(repository)
                .authenticator(authenticator)
                .build();
    }

    @Test
    public void getPlayer() throws UnirestException {
        Random random = new Random();

        String userName = "userName";
        String userPassword = "userPassword";

        Player player = Player.builder()
                .userId(UUID.randomUUID())
                .instanceId(UUID.randomUUID())
                .name("PlayerName-" + UUID.randomUUID())
                .position(Position.builder()
                        .x(random.ints(0, 1000).findFirst().getAsInt())
                        .z(random.ints(0, 1000).findFirst().getAsInt())
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

        when(repository.find(player.getId())).thenReturn(Optional.of(player));
        when(authenticator.authenticate(userName, userPassword, player.getId())).thenReturn(true);

        PlayerEntity expected = PlayerEntity.of(player);
        PlayerEntity result = fromJson(get("http://localhost:4567/players/" + player.getId())
                .header("x-userName", userName)
                .header("x-userPassword", userPassword)
                .asString()
                .getBody(), PlayerEntity.class);

        assertThat(result, equalTo(expected));
    }
}
