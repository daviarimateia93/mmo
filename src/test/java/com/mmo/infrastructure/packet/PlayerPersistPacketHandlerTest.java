package com.mmo.infrastructure.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.attribute.Attributes;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.player.Player;
import com.mmo.core.stat.Stats;
import com.mmo.infrastructure.mongo.MongoServer;

public class PlayerPersistPacketHandlerTest {

    private static Map map;
    private static PlayerPersistPacketHandler packetHandler;

    @BeforeAll
    public static void setup() {
        MongoServer.getInstance().start();

        map = mock(Map.class);
        packetHandler = new PlayerPersistPacketHandler();
    }

    @AfterAll
    public static void clear() {
        MongoServer.getInstance().stop();
    }

    @Test
    public void findAndPersist() {
        UUID instanceId = UUID.randomUUID();

        PlayerPersistPacket packet = PlayerPersistPacket.builder()
                .source(instanceId)
                .player(Player.builder()
                        .instanceId(instanceId)
                        .name("PlayerName-" + UUID.randomUUID())
                        .position(Position.builder()
                                .x(50L)
                                .y(10L)
                                .z(1L)
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
                        .build())
                .build();

        assertThat(packetHandler.find(packet.getPlayer().getId()), is(Optional.empty()));

        packetHandler.persist(packet.getPlayer());

        assertThat(packetHandler.find(packet.getPlayer().getId()), is(Optional.of(packet.getPlayer())));
    }

    @Test
    public void findAndHandle() {
        UUID instanceId = UUID.randomUUID();

        PlayerPersistPacket packet = PlayerPersistPacket.builder()
                .source(instanceId)
                .player(Player.builder()
                        .instanceId(instanceId)
                        .name("PlayerName-" + UUID.randomUUID())
                        .position(Position.builder()
                                .x(50L)
                                .y(10L)
                                .z(1L)
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
                        .build())
                .build();

        assertThat(packetHandler.find(packet.getPlayer().getId()), is(Optional.empty()));

        packetHandler.handle(map, packet);

        assertThat(packetHandler.find(packet.getPlayer().getId()), is(Optional.of(packet.getPlayer())));
    }
}
