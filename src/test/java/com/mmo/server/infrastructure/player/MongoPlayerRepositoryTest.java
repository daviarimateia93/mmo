package com.mmo.server.infrastructure.player;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.mongo.MongoServer;

public class MongoPlayerRepositoryTest {

    private static MongoPlayerRepository repository;

    @BeforeAll
    public static void setup() {
        MongoServer.getInstance().start();

        repository = new MongoPlayerRepository();
    }

    @AfterAll
    public static void clear() {
        MongoServer.getInstance().stop();
    }

    @Test
    public void findAndPersist() {
        Player player = Player.builder()
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

        assertThat(repository.find(player.getId()), is(Optional.empty()));

        repository.persist(player);

        assertThat(repository.find(player.getId()), is(Optional.of(player)));
    }
}
