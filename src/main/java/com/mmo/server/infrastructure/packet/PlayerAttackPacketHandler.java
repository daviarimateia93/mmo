package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.packet.PacketHandler;
import com.mmo.server.core.packet.PlayerAttackPacket;
import com.mmo.server.core.player.Player;

public class PlayerAttackPacketHandler
        extends com.mmo.server.infrastructure.packet.PacketHandler
        implements PacketHandler<PlayerAttackPacket> {

    @Override
    public void handle(PlayerAttackPacket packet) {
        Player source = getMap().getEntity(packet.getSource(), Player.class);
        Animate target = getMap().getEntity(packet.getTarget(), Animate.class);

        source.attack(target);
    }
}
