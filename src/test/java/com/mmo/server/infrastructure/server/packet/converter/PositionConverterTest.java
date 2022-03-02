package com.mmo.server.infrastructure.server.packet.converter;

import static com.mmo.server.infrastructure.server.packet.converter.PositionConverter.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.map.Position;
import com.mmo.server.infrastructure.map.PositionDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PositionConverterTest {

    @Test
    public void readAndWrite() {
        PositionDTO expected = PositionDTO.of(Position.builder()
                .x(10)
                .z(15)
                .build());

        PositionDTO result = null;

        try (PacketWriter writer = new PacketWriter()) {
            write(writer, expected);
            try (PacketReader reader = new PacketReader(writer.toBytes())) {
                result = read(reader);
            }
        }

        assertThat(result, equalTo(expected));
    }
}
