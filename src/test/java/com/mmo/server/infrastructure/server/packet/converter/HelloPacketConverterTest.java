package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.HelloPacket;

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
