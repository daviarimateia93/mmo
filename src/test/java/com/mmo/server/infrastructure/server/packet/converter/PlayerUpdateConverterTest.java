package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.server.packet.PacketReader;

public class PlayerUpdateConverterTest {

    public static PlayerUpdatePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new PlayerUpdatePacketConverter();
    }

    @Test
    public void write() {
        Player expected = Player.builder()
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

        byte[] result = converter.write(PlayerUpdatePacket.builder()
                .source(expected.getInstanceId())
                .player(expected)
                .build());

        try (PacketReader reader = new PacketReader(result)) {
            assertThat(reader.readUTF(), equalTo(expected.getName()));
            assertThat(PositionConverter.read(reader), equalTo(expected.getPosition()));
            assertThat(StatsConverter.read(reader), equalTo(expected.getStats()));
            assertThat(AttributesConverter.read(reader), equalTo(expected.getAttributes()));
            assertThat(reader.readBoolean(), equalTo(expected.isAlive()));
            assertThat(reader.readBoolean(), equalTo(expected.isMoving()));
            assertThat(PositionConverter.read(reader), equalTo(AnimateConverter.POSITION_NULL));
            assertThat(reader.readBoolean(), equalTo(expected.isAttacking()));
            assertThat(reader.readUUID(), equalTo(AnimateConverter.UUID_NULL));
        }
    }
}
