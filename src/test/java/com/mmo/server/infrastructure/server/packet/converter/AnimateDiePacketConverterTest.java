package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.AnimateDiePacket;

public class AnimateDiePacketConverterTest {

    public static AnimateDiePacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new AnimateDiePacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        AnimateDiePacket expected = AnimateDiePacket.builder()
                .source(source)
                .killedBy(UUID.randomUUID())
                .build();

        AnimateDiePacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
