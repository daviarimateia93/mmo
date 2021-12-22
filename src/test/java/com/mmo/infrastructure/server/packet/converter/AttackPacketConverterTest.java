package com.mmo.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.packet.AttackPacket;

public class AttackPacketConverterTest {

    public static AttackPacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new AttackPacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        AttackPacket expected = AttackPacket.builder()
                .source(source)
                .target(UUID.randomUUID())
                .build();

        AttackPacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
