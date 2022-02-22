package com.mmo.server.infrastructure.packet;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.game.GameRunnerMapMocker;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.core.player.Player;

public class PlayerMovePacketHandlerTest {

    private static Map map;
    private static PlayerMovePacket packet;
    private static PlayerMovePacketHandler packetHandler;
    private static Player source;

    @BeforeAll
    public static void setup() {
        map = GameRunnerMapMocker.run();
        packet = PlayerMovePacket.builder()
                .source(UUID.randomUUID())
                .target(Position.builder()
                        .x(10)
                        .z(15)
                        .build())
                .build();

        packetHandler = new PlayerMovePacketHandler();

        source = mock(Player.class);

        when(map.getEntity(packet.getSource(), Player.class)).thenReturn(source);
    }

    @AfterAll
    private static void clear() {
        GameRunnerMapMocker.stop();
    }

    @Test
    public void handle() {
        packetHandler.handle(packet);

        verify(source).move(packet.getTarget());
    }
}
