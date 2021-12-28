package com.mmo.server.infrastructure.server;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.server.infrastructure.server.packet.DisconnectPacket;

public class DisconnectPacketTest {

    @Test
    public void getAlias() {
        DisconnectPacket packet = DisconnectPacket.builder()
                .source(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("DISCONNECT"));
    }
}
