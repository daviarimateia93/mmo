package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.GoodByePacket;

public class GoodByePacketConverterTest {

    public static GoodByePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new GoodByePacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        GoodByePacket expected = GoodByePacket.builder()
                .source(source)
                .build();

        GoodByePacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
