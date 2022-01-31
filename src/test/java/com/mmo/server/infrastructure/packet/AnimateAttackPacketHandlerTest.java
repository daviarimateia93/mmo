package com.mmo.server.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.game.GameRunnerMapMocker;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.packet.AnimateAttackPacket;

public class AnimateAttackPacketHandlerTest {

    private static Map map;
    private static AnimateAttackPacket packet;
    private static AnimateAttackPacketHandler packetHandler;
    private static Animate source;
    private static Animate target;

    @BeforeAll
    public static void setup() {
        map = GameRunnerMapMocker.run();
        packet = AnimateAttackPacket.builder()
                .source(UUID.randomUUID())
                .target(UUID.randomUUID())
                .build();

        packetHandler = new AnimateAttackPacketHandler();

        source = mock(Animate.class);
        target = mock(Animate.class);

        when(map.getEntity(packet.getSource(), Animate.class)).thenReturn(source);
        when(map.getEntity(packet.getTarget(), Animate.class)).thenReturn(target);
    }

    @AfterAll
    private static void clear() {
        GameRunnerMapMocker.stop();
    }

    @Test
    public void handle() {
        packetHandler.handle(packet);

        verify(source).attack(target);
    }
}
