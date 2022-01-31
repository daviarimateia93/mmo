package com.mmo.server.core.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.infrastructure.server.TestPacket;

public class PacketHandlerDelegatorTest {

    private static TestPacket packet;

    @BeforeAll
    public static void setup() {
        UUID source = UUID.randomUUID();
        String property1 = "prop";
        int property2 = 3;

        packet = TestPacket.builder()
                .source(source)
                .property1(property1)
                .property2(property2)
                .build();
    }

    @Test
    public void delegate() {
        TestPacketHandler handler = new TestPacketHandler();

        PacketHandlerDelegator delegator = PacketHandlerDelegator.getInstance();
        delegator.bind(TestPacket.class, handler);
        delegator.delegate(packet);

        assertThat(handler.getPacket(), equalTo(packet));
    }
}
