package com.mmo.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.AnimateMovePacket;

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
                        .x(10L)
                        .y(15L)
                        .z(20L)
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
