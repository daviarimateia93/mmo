package com.mmo.server.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.AnimateMovePacket;

public class AnimateMovePacketHandlerTest {

    private static Map map;
    private static AnimateMovePacket packet;
    private static AnimateMovePacketHandler packetHandler;
    private static Animate source;

    @BeforeAll
    public static void setup() {
        map = mock(Map.class);
        packet = AnimateMovePacket.builder()
                .source(UUID.randomUUID())
                .target(Position.builder()
                        .x(10)
                        .y(15)
                        .z(20)
                        .build())
                .build();

        packetHandler = new AnimateMovePacketHandler();

        source = mock(Animate.class);

        when(map.getEntity(packet.getSource(), Animate.class)).thenReturn(source);
    }

    @Test
    public void handle() {
        packetHandler.handle(map, packet);

        verify(source).move(packet.getTarget());
    }
}
