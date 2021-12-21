package com.mmo.infrastructure.map.server.handler;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Position;
import com.mmo.infrastructure.map.packet.MovePacket;
import com.mmo.infrastructure.map.server.MapServer;

public class MovePacketHandler implements PacketHandler<MovePacket> {

    @Override
    public void handle(MapServer server, MovePacket packet) {
        Animate source = server.getMap().getEntity(packet.getSource(), Animate.class);
        Position target = packet.getTarget();

        source.move(target);
    }
}