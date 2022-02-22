package com.mmo.server.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class PlayerAttackPacketTest {

    @Test
    public void getAlias() {
        PlayerAttackPacket packet = PlayerAttackPacket.builder()
                .source(UUID.randomUUID())
                .target(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("PLAYER_ATTACK"));
    }
}
