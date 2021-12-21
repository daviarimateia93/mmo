package com.mmo.infrastructure.map.server.handler;

import com.mmo.core.animate.Animate;
import com.mmo.infrastructure.map.packet.AttackPacket;
import com.mmo.infrastructure.map.server.MapServer;

public class AttackPacketHandler implements PacketHandler<AttackPacket> {

    @Override
    public void handle(MapServer server, AttackPacket packet) {
        Animate source = server.getMap().getEntity(packet.getSource(), Animate.class);
        Animate target = server.getMap().getEntity(packet.getTarget(), Animate.class);

        source.attack(target);
    }
}
