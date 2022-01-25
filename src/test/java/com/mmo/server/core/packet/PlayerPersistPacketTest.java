package com.mmo.server.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.stat.Stats;

public class PlayerPersistPacketTest {

    @Test
    public void getAlias() {
        PlayerPersistPacket packet = PlayerPersistPacket.builder()
                .source(UUID.randomUUID())
                .player(Player.builder()
                        .userId(UUID.randomUUID())
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
                        .build())
                .build();

        assertThat(packet.getAlias(), is("PLAYER_PERSIST"));
    }
}
