package com.mmo.infrastructure.map.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.infrastructure.map.MapServer;

public class AttackPacketHandlerTest {

    private static MapServer server;
    private static Map map;
    private static AttackPacket packet;
    private static AttackPacketHandler packetHandler;
    private static Animate source;
    private static Animate target;

    @BeforeAll
    public static void setup() {
        server = mock(MapServer.class);
        map = mock(Map.class);
        packet = AttackPacket.builder()
                .source(UUID.randomUUID())
                .target(UUID.randomUUID())
                .build();

        packetHandler = new AttackPacketHandler();

        source = mock(Animate.class);
        target = mock(Animate.class);

        when(server.getMap()).thenReturn(map);
        when(map.getEntity(packet.getSource(), Animate.class)).thenReturn(source);
        when(map.getEntity(packet.getTarget(), Animate.class)).thenReturn(target);
    }

    @Test
    public void handle() {
        packetHandler.handle(server, packet);

        verify(source).attack(target);
    }
}
