package com.mmo.infrastructure.packet;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.packet.AttackPacket;
import com.mmo.core.packet.PacketHandler;

public class AttackPacketHandler implements PacketHandler<AttackPacket> {

    @Override
    public void handle(Map map, AttackPacket packet) {
        Animate source = map.getEntity(packet.getSource(), Animate.class);
        Animate target = map.getEntity(packet.getTarget(), Animate.class);

        source.attack(target);
    }
}
