package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.packet.HelloPacket;
import com.mmo.infrastructure.server.packet.converter.HelloPacketConverter;

public class HelloPacketConverterTest {

    public static HelloPacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new HelloPacketConverter();
    }

    @Test
    public void fromBytesAndToBytes() {
        UUID source = UUID.randomUUID();

        HelloPacket expected = HelloPacket.builder()
                .source(source)
                .build();

        HelloPacket result = converter.fromBytes(source, converter.toBytes(expected));

        assertThat(result, equalTo(expected));
    }
}
