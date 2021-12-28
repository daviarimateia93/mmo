package com.mmo.server.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class AnimateDiePacketTest {

    @Test
    public void getAlias() {
        AnimateDiePacket packet = AnimateDiePacket.builder()
                .source(UUID.randomUUID())
                .killedBy(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("ANIMATE_DIE"));
    }
}
