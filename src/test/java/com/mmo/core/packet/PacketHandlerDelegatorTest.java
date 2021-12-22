package com.mmo.core.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.map.Map;
import com.mmo.infrastructure.server.TestPacket;

public class PacketHandlerDelegatorTest {

    private static Map map;
    private static TestPacket packet;

    @BeforeAll
    public static void setup() {
        UUID source = UUID.randomUUID();
        String property1 = "prop";
        int property2 = 3;

        map = mock(Map.class);
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
        delegator.delegate(map, packet);

        assertThat(handler.getMap(), equalTo(map));
        assertThat(handler.getPacket(), equalTo(packet));
    }
}
