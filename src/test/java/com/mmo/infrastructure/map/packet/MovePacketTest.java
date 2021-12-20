package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.core.map.Position;

public class MovePacketTest {

    @Test
    public void serializeAndDeserialize() {
        UUID source = UUID.randomUUID();

        MovePacket expected = MovePacket.builder()
                .source(source)
                .target(Position.builder()
                        .x(10L)
                        .y(15L)
                        .z(20L)
                        .build())
                .build();

        MovePacket result = MovePacket.binaryBuilder()
                .build(source, expected.toBytes());

        assertThat(result, equalTo(expected));
        assertThat(result.getAlias(), equalTo("MOVE"));
    }
}
