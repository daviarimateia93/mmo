package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.infrastructure.server.TestPacket.TestPacketBuilder;

public class PacketFactoryTest {

    @Test
    public void registerAndGetBuilderByUUID() {
        TestPacketBuilder builder = TestPacket.builder();

        UUID source = UUID.randomUUID();
        String property1 = "hahha";
        Integer property2 = 7;

        Packet expected = builder.build(source, property1, property2);

        PacketFactory.getInstance().register(expected.getAliasAsUUID(), builder);

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
        TestPacketBuilder builder = TestPacket.builder();

        UUID source = UUID.randomUUID();
        String property1 = "hehhe";
        Integer property2 = 8;

        Packet expected = builder.build(source, property1, property2);

        PacketFactory.getInstance().register(expected.getAlias(), builder);

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
        TestPacketBuilder builder = TestPacket.builder();

        UUID source = UUID.randomUUID();
        String property1 = "hihihi";
        Integer property2 = 9;

        Packet expected = builder.build(source, property1, property2);

        PacketFactory.getInstance().register(expected, builder);

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
