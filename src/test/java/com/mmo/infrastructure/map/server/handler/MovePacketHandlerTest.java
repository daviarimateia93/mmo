package com.mmo.infrastructure.map.server.handler;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.MovePacket;
import com.mmo.infrastructure.map.server.MapServer;

public class MovePacketHandlerTest {

    private static MapServer server;
    private static Map map;
    private static MovePacket packet;
    private static MovePacketHandler packetHandler;
    private static Animate source;

    @BeforeAll
    public static void setup() {
        server = mock(MapServer.class);
        map = mock(Map.class);
        packet = MovePacket.builder()
                .source(UUID.randomUUID())
                .target(Position.builder()
                        .x(10L)
                        .y(15L)
                        .z(20L)
                        .build())
                .build();

        packetHandler = new MovePacketHandler();

        source = mock(Animate.class);

        when(server.getMap()).thenReturn(map);
        when(map.getEntity(packet.getSource(), Animate.class)).thenReturn(source);
    }

    @Test
    public void handle() {
        packetHandler.handle(server, packet);

        verify(source).move(packet.getTarget());
    }
}
