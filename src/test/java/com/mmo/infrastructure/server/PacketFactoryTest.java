package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import lombok.EqualsAndHashCode;
import lombok.ToString;

public class PacketFactoryTest {

    @Test
    public void registerAndGetBuilderByUUID() {
        TestPacket.TestPacketBuilder builder = TestPacket.builder();

        String property1 = "hahha";
        Integer property2 = 7;

        Packet expected = builder.build(property1, property2);

        PacketFactory.getInstance().register(expected.getAliasAsUUID(), builder);

        Packet result1 = PacketFactory.getInstance().getPacket(expected.getAliasAsUUID(), expected.toBytes());
        Packet result2 = PacketFactory.getInstance().getPacket(expected.getAlias(), expected.toBytes());

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @Test
    public void registerAndGetBuilderByString() {
        TestPacket.TestPacketBuilder builder = TestPacket.builder();

        String property1 = "hehhe";
        Integer property2 = 8;

        Packet expected = builder.build(property1, property2);

        PacketFactory.getInstance().register(expected.getAlias(), builder);

        Packet result1 = PacketFactory.getInstance().getPacket(expected.getAliasAsUUID(), expected.toBytes());
        Packet result2 = PacketFactory.getInstance().getPacket(expected.getAlias(), expected.toBytes());

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @Test
    public void registerAndGetBuilderByPacket() {
        TestPacket.TestPacketBuilder builder = TestPacket.builder();

        String property1 = "hihihi";
        Integer property2 = 9;

        Packet expected = builder.build(property1, property2);

        PacketFactory.getInstance().register(expected, builder);

        Packet result1 = PacketFactory.getInstance().getPacket(expected.getAliasAsUUID(), expected.toBytes());
        Packet result2 = PacketFactory.getInstance().getPacket(expected.getAlias(), expected.toBytes());

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @EqualsAndHashCode
    @ToString
    private static class TestPacket implements Packet {

        public final static UUID ID = UUID.randomUUID();
        public final static String ALIAS = "test";

        private final String property1;
        private final Integer property2;

        private TestPacket(String property1, Integer property2) {
            this.property1 = property1;
            this.property2 = property2;
        }

        @Override
        public UUID getId() {
            return ID;
        }

        @Override
        public String getAlias() {
            return ALIAS;
        }

        @Override
        public byte[] toBytes() {
            return String.format("%s%d", property1, property2).getBytes();
        }

        public static TestPacketBuilder builder() {
            return new TestPacketBuilder();
        }

        public static class TestPacketBuilder implements PacketBuilder<TestPacket> {

            public TestPacket build(String property1, Integer property2) {
                return new TestPacket(property1, property2);
            }

            @Override
            public TestPacket build(byte[] bytes) {
                String string = new String(bytes);

                String property1 = string.substring(0, string.length() - 1);
                int property2 = Integer.valueOf(string.substring(string.length() - 1));

                return new TestPacket(property1, property2);
            }
        }
    }
}
