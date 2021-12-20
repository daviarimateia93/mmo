package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class HelloPacketTest {

    @Test
    public void serializeAndDeserialize() {
        UUID source = UUID.randomUUID();

        HelloPacket expected = new HelloPacket(source);

        HelloPacket result = HelloPacket.binaryBuilder()
                .build(source, expected.toBytes());

        assertThat(result, equalTo(expected));
        assertThat(result.getAlias(), equalTo("HELLO"));
    }
}
