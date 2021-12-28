package com.mmo.server.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class AnimateAttackPacketTest {

    @Test
    public void getAlias() {
        AnimateAttackPacket packet = AnimateAttackPacket.builder()
                .source(UUID.randomUUID())
                .target(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("ANIMATE_ATTACK"));
    }
}
