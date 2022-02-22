package com.mmo.server.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.map.Position;

public class PlayerMovePacketTest {

    @Test
    public void getAlias() {
        PlayerMovePacket packet = PlayerMovePacket.builder()
                .source(UUID.randomUUID())
                .target(Position.builder()
                        .x(10)
                        .z(11)
                        .build())
                .build();

        assertThat(packet.getAlias(), is("PLAYER_MOVE"));
    }
}
