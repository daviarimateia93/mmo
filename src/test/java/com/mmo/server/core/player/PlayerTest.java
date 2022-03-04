package com.mmo.server.core.player;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.game.GameRunnerMapMocker;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerPersistPacket;
import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.stat.Stats;

public class PlayerTest {

    private static Player player;
    private static Map map;

    @BeforeAll
    public static void setup() {
        player = Player.builder()
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
                .build();

        map = GameRunnerMapMocker.run();
    }

    @AfterAll
    private static void clear() {
        GameRunnerMapMocker.stop();
    }

    @AfterEach
    private void clearEach() {
        clearInvocations(map);
    }

    @Test
    public void moveDispatchUpdatePacket() {
        player.move(Position.builder()
                .x(10)
                .z(20)
                .build());

        verify(map).dispatch(any(PlayerUpdatePacket.class));
    }

    @Test
    public void attackDispatchUpdatePacket() {
        player.attack(mock(Animate.class));

        verify(map).dispatch(any(PlayerUpdatePacket.class));
    }

    @Test
    public void onDieDispatchPersistPacket() {
        player.onDie(mock(Animate.class));

        verify(map).dispatch(any(PlayerPersistPacket.class));
    }
}
