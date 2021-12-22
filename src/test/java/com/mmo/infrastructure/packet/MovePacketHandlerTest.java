package com.mmo.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.MovePacket;

public class MovePacketHandlerTest {

    private static Map map;
    private static MovePacket packet;
    private static MovePacketHandler packetHandler;
    private static Animate source;

    @BeforeAll
    public static void setup() {
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

        when(map.getEntity(packet.getSource(), Animate.class)).thenReturn(source);
    }

    @Test
    public void handle() {
        packetHandler.handle(map, packet);

        verify(source).move(packet.getTarget());
    }
}
