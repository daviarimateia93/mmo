package com.mmo.server.infrastructure.server.packet.converter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.packet.HelloPacket;

public class HelloPacketConverterTest {

    private static HelloPacketConverter converter;

    @BeforeAll
    public static void setup() {
        converter = new HelloPacketConverter();
    }

    @Test
    public void readAndWrite() {
        UUID source = UUID.randomUUID();

        HelloPacket expected = HelloPacket.builder()
                .source(source)
                .userName("userName")
                .userPassword("userPassword")
                .build();

        HelloPacket result = converter.read(source, OffsetDateTime.now(), converter.write(expected));

        assertThat(result, equalTo(expected));
    }
}
