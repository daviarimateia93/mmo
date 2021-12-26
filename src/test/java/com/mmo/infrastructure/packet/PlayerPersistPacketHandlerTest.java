package com.mmo.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.attribute.Attributes;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.player.Player;
import com.mmo.core.player.PlayerRepository;
import com.mmo.core.stat.Stats;

public class PlayerPersistPacketHandlerTest {

    private static Map map;
    private static PlayerRepository repository;
    private static PlayerPersistPacketHandler packetHandler;

    @BeforeAll
    public static void setup() {
        map = mock(Map.class);
        repository = mock(PlayerRepository.class);
        packetHandler = new PlayerPersistPacketHandler(repository);
    }

    @Test
    public void handle() {
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

        packetHandler.handle(map, packet);
        verify(repository).persist(packet.getPlayer());
    }
}
