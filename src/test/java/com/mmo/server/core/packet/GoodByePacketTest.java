package com.mmo.server.core.packet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class GoodByePacketTest {

    @Test
    public void getAlias() {
        GoodByePacket packet = GoodByePacket.builder()
                .source(UUID.randomUUID())
                .build();

        assertThat(packet.getAlias(), is("GOOD_BYE"));
    }
}
