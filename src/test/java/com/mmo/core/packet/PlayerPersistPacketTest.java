package com.mmo.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class PlayerPersistPacketTest {

    @Test
    public void getAlias() {
        PlayerPersistPacket packet = PlayerPersistPacket.builder()
                .source(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("PLAYER_PERSIST"));
    }
}
