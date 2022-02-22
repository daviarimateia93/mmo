package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PacketHandler;
import com.mmo.server.core.packet.PlayerMovePacket;
import com.mmo.server.core.player.Player;

public class PlayerMovePacketHandler
        extends com.mmo.server.infrastructure.packet.PacketHandler
        implements PacketHandler<PlayerMovePacket> {

    @Override
    public void handle(PlayerMovePacket packet) {
        Player source = getMap().getEntity(packet.getSource(), Player.class);
        Position target = packet.getTarget();

        source.move(target);
    }
}