package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.packet.AnimateAttackPacket;
import com.mmo.server.core.packet.PacketHandler;

public class AnimateAttackPacketHandler implements PacketHandler<AnimateAttackPacket> {

    @Override
    public void handle(Map map, AnimateAttackPacket packet) {
        Animate source = map.getEntity(packet.getSource(), Animate.class);
        Animate target = map.getEntity(packet.getTarget(), Animate.class);

        source.attack(target);
    }
}
