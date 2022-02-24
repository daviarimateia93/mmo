package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.PlayerAttackPacket;

public class PlayerAttackPacketConverterTest {

    public static PlayerAttackPacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new PlayerAttackPacketConverter();
    }

    @Test
    public void readAndWrite() {
        UUID source = UUID.randomUUID();

        PlayerAttackPacket expected = PlayerAttackPacket.builder()
                .source(source)
                .target(UUID.randomUUID())
                .build();

        PlayerAttackPacket result = converter.read(source, converter.write(expected));

        assertThat(result, equalTo(expected));
    }
}
