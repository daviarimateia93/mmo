package com.mmo.server.infrastructure.server.packet.converter;

import static com.mmo.server.infrastructure.server.packet.converter.StatsConverter.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class StatsConverterTest {

    @Test
    public void readAndWrite() {
        Stats expected = Stats.builder()
                .strength(10)
                .dexterity(10)
                .intelligence(10)
                .concentration(10)
                .sense(10)
                .charm(10)
                .build();

        Stats result = null;

        try (PacketWriter writer = new PacketWriter()) {
            write(writer, expected);
            try (PacketReader reader = new PacketReader(writer.toBytes())) {
                result = read(reader);
            }
        }

        assertThat(result, equalTo(expected));
    }
}
