package com.mmo.infrastructure.packet;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.packet.AnimateAttackPacket;
import com.mmo.core.packet.PacketHandler;

public class AnimateAttackPacketHandler implements PacketHandler<AnimateAttackPacket> {

    @Override
    public void handle(Map map, AnimateAttackPacket packet) {
        Animate source = map.getEntity(packet.getSource(), Animate.class);
        Animate target = map.getEntity(packet.getTarget(), Animate.class);

        source.attack(target);
    }
}
