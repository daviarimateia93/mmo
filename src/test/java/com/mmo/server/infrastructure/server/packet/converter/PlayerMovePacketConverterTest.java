package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerMovePacket;

public class PlayerMovePacketConverterTest {

    public static PlayerMovePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new PlayerMovePacketConverter();
    }

    @Test
    public void readAndWrite() {
        UUID source = UUID.randomUUID();

        PlayerMovePacket expected = PlayerMovePacket.builder()
                .source(source)
                .target(Position.builder()
                        .x(10)
                        .z(15)
                        .build())
                .build();

        PlayerMovePacket result = converter.read(source, converter.write(expected));

        assertThat(result, equalTo(expected));
    }
}
