package com.mmo.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.core.map.Position;

public class AnimateMovePacketTest {

    @Test
    public void getAlias() {
        AnimateMovePacket packet = AnimateMovePacket.builder()
                .source(UUID.randomUUID())
                .target(Position.builder()
                        .x(10L)
                        .y(11L)
                        .z(12L)
                        .build())
                .build();

        assertThat(packet.getAlias(), is("ANIMATE_MOVE"));
    }
}
