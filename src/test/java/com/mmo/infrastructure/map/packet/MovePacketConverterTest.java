package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.map.Position;
import com.mmo.core.packet.MovePacket;

public class MovePacketConverterTest {

    public static MovePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new MovePacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        MovePacket expected = MovePacket.builder()
                .source(source)
                .target(Position.builder()
                        .x(10L)
                        .y(15L)
                        .z(20L)
                        .build())
                .build();

        MovePacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
