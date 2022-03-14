package com.mmo.server.infrastructure.server.packet.converter;

import static com.mmo.server.infrastructure.server.packet.converter.AnimateConverter.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.infrastructure.animate.AnimateDTO;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class AnimateConverterTest {

    @Test
    public void readAndWrite() {
        AnimateDTO expected = AnimateDTO.of(new AnimateImpl());
        AnimateDTO result = null;

        try (PacketWriter writer = new PacketWriter()) {
            write(writer, expected);
            try (PacketReader reader = new PacketReader(writer.toBytes())) {
                result = read(reader);
            }
        }

        assertThat(result, equalTo(expected));
    }

    @Test
    public void readAndWriteWhenAttacking() {
        AnimateImpl animate = new AnimateImpl();
        animate.attack(new AnimateImpl());

        AnimateDTO expected = AnimateDTO.of(animate);
        AnimateDTO result = null;

        try (PacketWriter writer = new PacketWriter()) {
            write(writer, expected);
            try (PacketReader reader = new PacketReader(writer.toBytes())) {
                result = read(reader);
            }
        }

        assertThat(result, equalTo(expected));
    }

    @Test
    public void readAndWriteWhenMoving() {
        AnimateImpl animate = new AnimateImpl();
        animate.move(Position.builder()
                .x(40)
                .z(15)
                .build());

        AnimateDTO expected = AnimateDTO.of(animate);
        AnimateDTO result = null;

        try (PacketWriter writer = new PacketWriter()) {
            write(writer, expected);
            try (PacketReader reader = new PacketReader(writer.toBytes())) {
                result = read(reader);
            }
        }

        assertThat(result, equalTo(expected));
    }

    private class AnimateImpl extends Animate {
        UUID id = UUID.randomUUID();
        UUID instanceId = UUID.randomUUID();
        String name = UUID.randomUUID().toString();

        Position position = Position.builder()
                .x(10)
                .z(15)
                .build();

        Attributes attributes = Attributes.builder()
                .hp(30)
                .mp(31)
                .attack(60)
                .defense(33)
                .magicDefense(34)
                .hitRate(35)
                .critical(36)
                .dodgeRate(37)
                .attackSpeed(50)
                .moveSpeed(2)
                .hpRecovery(40)
                .mpRecovery(41)
                .attackRange(3)
                .build();

        AnimateImpl() {

        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public UUID getInstanceId() {
            return instanceId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public Attributes getAttributes() {
            return attributes;
        }
    }
}
