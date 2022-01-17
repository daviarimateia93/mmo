package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.AnimateMovePacket;

public class AnimateMovePacketConverterTest {

    public static AnimateMovePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new AnimateMovePacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        AnimateMovePacket expected = AnimateMovePacket.builder()
                .source(source)
                .target(Position.builder()
                        .x(10)
                        .y(15)
                        .build())
                .build();

        AnimateMovePacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
