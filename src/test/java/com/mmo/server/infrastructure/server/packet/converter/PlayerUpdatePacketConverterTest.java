package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.game.GameRunnerMapMocker;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.stat.Stats;

public class PlayerUpdatePacketConverterTest {

    private static PlayerUpdatePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new PlayerUpdatePacketConverter();
        GameRunnerMapMocker.run();
    }

    @AfterAll
    private static void clear() {
        GameRunnerMapMocker.stop();
    }

    @Test
    public void readAndWrite() {
        UUID source = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        PlayerUpdatePacket expected = PlayerUpdatePacket.builder()
                .source(source)
                .player(newPlayer(source, userId))
                .build();

        PlayerUpdatePacket result = converter.read(source, OffsetDateTime.now(), converter.write(expected));

        assertThat(result, equalTo(expected));
    }

    @Test
    public void readAndWriteWhenAttacking() {
        UUID source = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Player player = newPlayer(source, userId);
        player.attack(newPlayer(UUID.randomUUID(), userId));

        PlayerUpdatePacket expected = PlayerUpdatePacket.builder()
                .source(source)
                .player(player)
                .build();

        PlayerUpdatePacket result = converter.read(source, OffsetDateTime.now(), converter.write(expected));

        assertThat(result, equalTo(expected));
    }

    @Test
    public void readAndWriteWhenMoving() {
        UUID source = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Player player = newPlayer(source, userId);
        player.move(Position.builder()
                .x(40)
                .z(15)
                .build());

        PlayerUpdatePacket expected = PlayerUpdatePacket.builder()
                .source(source)
                .player(player)
                .build();

        PlayerUpdatePacket result = converter.read(source, OffsetDateTime.now(), converter.write(expected));

        assertThat(result, equalTo(expected));
    }

    private Player newPlayer(UUID source, UUID userId) {
        return Player.builder()
                .userId(userId)
                .instanceId(source)
                .name("PlayerName-" + source)
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
    }
}
