package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.core.packet.Packet;
import com.mmo.infrastructure.server.packet.PacketGateway;

public class PacketFactoryTest {

    @Test
    public void bindAndInAndOutBuilderByUUID() {
        UUID source = UUID.randomUUID();
        String property1 = "hahha";
        Integer property2 = 7;

        Packet expected = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();

        PacketGateway.getInstance().bind(expected.getAliasAsUUID(), TestPacket.converter());

        byte[] expectedBytes = PacketGateway.getInstance().out(expected);

        Packet result1 = PacketGateway.getInstance().in(
                expected.getAliasAsUUID(),
                expected.getSource(),
                expectedBytes);

        Packet result2 = PacketGateway.getInstance().in(
                expected.getAlias(),
                expected.getSource(),
                expectedBytes);

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @Test
    public void bindAndInAndOutBuilderByString() {
        UUID source = UUID.randomUUID();
        String property1 = "hehhe";
        Integer property2 = 8;

        Packet expected = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();

        PacketGateway.getInstance().bind(expected.getAlias(), TestPacket.converter());

        byte[] expectedBytes = PacketGateway.getInstance().out(expected);

        Packet result1 = PacketGateway.getInstance().in(
                expected.getAliasAsUUID(),
                expected.getSource(),
                expectedBytes);

        Packet result2 = PacketGateway.getInstance().in(
                expected.getAlias(),
                expected.getSource(),
                expectedBytes);

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }

    @Test
    public void bindAndInAndOutBuilderByPacket() {
        UUID source = UUID.randomUUID();
        String property1 = "hihihi";
        Integer property2 = 9;

        Packet expected = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();

        PacketGateway.getInstance().bind(expected, TestPacket.converter());

        byte[] expectedBytes = PacketGateway.getInstance().out(expected);

        Packet result1 = PacketGateway.getInstance().in(
                expected.getAliasAsUUID(),
                expected.getSource(),
                expectedBytes);

        Packet result2 = PacketGateway.getInstance().in(
                expected.getAlias(),
                expected.getSource(),
                expectedBytes);

        assertThat(result1, equalTo(expected));
        assertThat(result2, equalTo(expected));
    }
}
