package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class GoodByePacketTest {

    @Test
    public void serializeAndDeserialize() {
        UUID source = UUID.randomUUID();

        GoodByePacket expected = GoodByePacket.builder()
                .source(source)
                .build();

        GoodByePacket result = GoodByePacket.binaryBuilder()
                .build(source, expected.toBytes());

        assertThat(result, equalTo(expected));
        assertThat(result.getAlias(), equalTo("GOOD_BYE"));
    }
}
