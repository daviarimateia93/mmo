package com.mmo.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class AttackPacketTest {

    @Test
    public void getAlias() {
        AttackPacket packet = AttackPacket.builder()
                .source(UUID.randomUUID())
                .target(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("ATTACK"));
    }
}
