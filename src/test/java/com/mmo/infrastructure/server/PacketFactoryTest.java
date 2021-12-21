package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class PacketFactoryTest {

    @Test
    public void registerAndGetBuilderByUUID() {
        UUID source = UUID.randomUUID();
        String property1 = "hahha";
        Integer property2 = 7;

        Packet expected = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();

        PacketFactory.getInstance().register(expected.getAliasAsUUID(), TestPacket.binaryBuilder());

        Packet result1 = PacketFactory.getInstance().getPacket(
                expected.getAliasAsUUID(),
                expected.getSource(),
                expected.toBytes());

        Packet result2 = PacketFactory.getInstance().getPacket(
                expected.getAlias(),
                expected.getSource(),
                expected.toBytes());

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @Test
    public void registerAndGetBuilderByString() {
        UUID source = UUID.randomUUID();
        String property1 = "hehhe";
        Integer property2 = 8;

        Packet expected = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();

        PacketFactory.getInstance().register(expected.getAlias(), TestPacket.binaryBuilder());

        Packet result1 = PacketFactory.getInstance().getPacket(
                expected.getAliasAsUUID(),
                expected.getSource(),
                expected.toBytes());

        Packet result2 = PacketFactory.getInstance().getPacket(
                expected.getAlias(),
                expected.getSource(),
                expected.toBytes());

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @Test
    public void registerAndGetBuilderByPacket() {
        UUID source = UUID.randomUUID();
        String property1 = "hihihi";
        Integer property2 = 9;

        Packet expected = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();

        PacketFactory.getInstance().register(expected, TestPacket.binaryBuilder());

        Packet result1 = PacketFactory.getInstance().getPacket(
                expected.getAliasAsUUID(),
                expected.getSource(),
                expected.toBytes());

        Packet result2 = PacketFactory.getInstance().getPacket(
                expected.getAlias(),
                expected.getSource(),
                expected.toBytes());

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }
}
