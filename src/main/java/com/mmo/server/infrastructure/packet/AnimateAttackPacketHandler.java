package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.packet.AnimateAttackPacket;
import com.mmo.server.core.packet.PacketHandler;

public class AnimateAttackPacketHandler
        extends com.mmo.server.infrastructure.packet.PacketHandler
        implements PacketHandler<AnimateAttackPacket> {

    @Override
    public void handle(AnimateAttackPacket packet) {
        Animate source = getMap().getEntity(packet.getSource(), Animate.class);
        Animate target = getMap().getEntity(packet.getTarget(), Animate.class);

        source.attack(target);
    }
}
