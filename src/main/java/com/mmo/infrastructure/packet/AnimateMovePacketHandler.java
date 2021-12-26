package com.mmo.infrastructure.packet;

import com.mmo.core.animate.Animate;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.AnimateMovePacket;
import com.mmo.core.packet.PacketHandler;

public class AnimateMovePacketHandler implements PacketHandler<AnimateMovePacket> {

    @Override
    public void handle(Map map, AnimateMovePacket packet) {
        Animate source = map.getEntity(packet.getSource(), Animate.class);
        Position target = packet.getTarget();

        source.move(target);
    }
}