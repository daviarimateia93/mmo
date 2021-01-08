package com.mmo.infrastructure.server;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class TestPacket implements Packet {

    public final static String ALIAS = "test";

    private final String property1;
    private final Integer property2;

    private TestPacket(String property1, Integer property2) {
        this.property1 = property1;
        this.property2 = property2;
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