package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.AnimateMovePacket;
import com.mmo.server.core.packet.PacketHandler;

public class AnimateMovePacketHandler implements PacketHandler<AnimateMovePacket> {

    @Override
    public void handle(Map map, AnimateMovePacket packet) {
        Animate source = map.getEntity(packet.getSource(), Animate.class);
        Position target = packet.getTarget();

        source.move(target);
    }
}