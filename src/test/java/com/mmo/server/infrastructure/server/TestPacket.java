package com.mmo.server.infrastructure.server;

import java.util.UUID;

import com.mmo.server.core.packet.NetworkPacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TestPacket implements NetworkPacket {

    public final static String ALIAS = "test";

    private final UUID source;
    private final Long timestamp;
    private final String property1;
    private final Integer property2;

    @Builder
    private TestPacket(
            @NonNull UUID source,
            @NonNull Long timestamp,
            @NonNull String property1,
            @NonNull Integer property2) {

        this.source = source;
        this.timestamp = timestamp;
        this.property1 = property1;
        this.property2 = property2;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

    public static TestPacketConverter converter() {
        return new TestPacketConverter();
    }

    public static class TestPacketConverter implements PacketConverter<TestPacket> {

        @Override
        public TestPacket fromBytes(UUID source, byte[] bytes) {
            String string = new String(bytes);

            String property1 = string.substring(0, string.length() - 1);
            int property2 = Integer.valueOf(string.substring(string.length() - 1));

            return TestPacket.builder()
                    .source(source)
                    .property1(property1)
                    .property2(property2)
                    .build();
        }

        @Override
        public byte[] toBytes(TestPacket packet) {
            return String.format("%s%d", packet.getProperty1(), packet.getProperty2()).getBytes();
        }
    }
}