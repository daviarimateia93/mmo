package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.infrastructure.map.MapServer;
import com.mmo.infrastructure.server.TestPacket;

public class PacketHandlerDelegatorTest {

    private static MapServer server;
    private static TestPacket packet;

    @BeforeAll
    public static void setup() {
        UUID source = UUID.randomUUID();
        String property1 = "prop";
        int property2 = 3;

        server = mock(MapServer.class);
        packet = TestPacket.builder().build(source, property1, property2);
    }

    @Test
    public void delegate() {
        TestPacketHandler handler = new TestPacketHandler();

        PacketHandlerDelegator delegator = PacketHandlerDelegator.getInstance();
        delegator.bind(TestPacket.class, handler);
        delegator.delegate(server, packet);

        assertThat(handler.getServer(), equalTo(server));
        assertThat(handler.getPacket(), equalTo(packet));
    }
}
