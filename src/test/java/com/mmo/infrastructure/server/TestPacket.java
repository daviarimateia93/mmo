package com.mmo.infrastructure.server;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TestPacket implements Packet {

    public final static String ALIAS = "test";

    private final UUID source;
    private final String property1;
    private final Integer property2;

    @Builder
    private TestPacket(
            @NonNull UUID source,
            @NonNull String property1,
            @NonNull Integer property2) {

        this.source = source;
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

    public static TestPacketBinaryBuilder binaryBuilder() {
        return new TestPacketBinaryBuilder();
    }

    public static class TestPacketBinaryBuilder implements PacketBinaryBuilder<TestPacket> {

        @Override
        public TestPacket build(UUID source, byte[] bytes) {
            String string = new String(bytes);

            String property1 = string.substring(0, string.length() - 1);
            int property2 = Integer.valueOf(string.substring(string.length() - 1));

            return TestPacket.builder()
                    .source(source)
                    .property1(property1)
                    .property2(property2)
                    .build();
        }
    }
}