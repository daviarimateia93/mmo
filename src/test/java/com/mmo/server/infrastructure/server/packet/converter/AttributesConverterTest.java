package com.mmo.server.infrastructure.server.packet.converter;

import static com.mmo.server.infrastructure.server.packet.converter.AttributesConverter.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.infrastructure.animate.AttributesDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class AttributesConverterTest {

    @Test
    public void readAndWrite() {
        AttributesDTO expected = AttributesDTO.of(Attributes.builder()
                .hp(30)
                .mp(31)
                .attack(42)
                .defense(33)
                .magicDefense(34)
                .hitRate(35)
                .critical(36)
                .dodgeRate(37)
                .attackSpeed(38)
                .moveSpeed(2)
                .hpRecovery(40)
                .mpRecovery(41)
                .attackRange(3)
                .build());

        AttributesDTO result = null;

        try (PacketWriter writer = new PacketWriter()) {
            write(writer, expected);
            try (PacketReader reader = new PacketReader(writer.toBytes())) {
                result = read(reader);
            }
        }

        assertThat(result, equalTo(expected));
    }
}
