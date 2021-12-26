package com.mmo.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.packet.AnimateAttackPacket;

public class AnimateAttackPacketConverterTest {

    public static AnimateAttackPacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new AnimateAttackPacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        AnimateAttackPacket expected = AnimateAttackPacket.builder()
                .source(source)
                .target(UUID.randomUUID())
                .build();

        AnimateAttackPacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
