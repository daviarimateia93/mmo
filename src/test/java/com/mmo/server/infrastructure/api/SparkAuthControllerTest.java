package com.mmo.server.infrastructure.api;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static com.mmo.server.infrastructure.api.Spark.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.mmo.server.infrastructure.api.SparkAuthController.TokenResponseDTO;
import com.mmo.server.infrastructure.security.Encryptor;

public class SparkAuthControllerTest {

    private static PlayerRepository repository;
    private static Encryptor encryptor;

    @BeforeAll
    public static void setup() {
        repository = mock(PlayerRepository.class);
        encryptor = mock(Encryptor.class);

        SparkAuthController.builder()
                .repository(repository)
                .encryptor(encryptor)
                .build();
    }

    @Test
    public void getToken() throws UnirestException {
        Random random = new Random();

        Player player = Player.builder()
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

        String token = "encrypted-token";

        when(repository.find(player.getId())).thenReturn(Optional.of(player));
        when(encryptor.encrypt(anyString())).thenReturn(token);

        TokenResponseDTO expected = new TokenResponseDTO(token);

        TokenResponseDTO result = fromJson(post("http://localhost:4567/auth/" + player.getId())
                .asString()
                .getBody(), TokenResponseDTO.class);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getTokenReturns401() throws UnirestException {
        when(repository.find(any())).thenReturn(Optional.empty());

        int expected = 404;
        int result = get("http://localhost:4567/auth/" + UUID.randomUUID())
                .asString()
                .getStatus();

        assertThat(result, equalTo(expected));
    }
}
