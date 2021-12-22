package com.mmo.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.packet.AttackPacket;

public class AttackPacketHandlerTest {

    private static Map map;
    private static AttackPacket packet;
    private static AttackPacketHandler packetHandler;
    private static Animate source;
    private static Animate target;

    @BeforeAll
    public static void setup() {
        map = mock(Map.class);
        packet = AttackPacket.builder()
                .source(UUID.randomUUID())
                .target(UUID.randomUUID())
                .build();

        packetHandler = new AttackPacketHandler();

        source = mock(Animate.class);
        target = mock(Animate.class);

        when(map.getEntity(packet.getSource(), Animate.class)).thenReturn(source);
        when(map.getEntity(packet.getTarget(), Animate.class)).thenReturn(target);
    }

    @Test
    public void handle() {
        packetHandler.handle(map, packet);

        verify(source).attack(target);
    }
}
