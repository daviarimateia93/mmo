package com.mmo.infrastructure.map.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.infrastructure.map.packet.GoodByePacket;
import com.mmo.infrastructure.map.server.MapServer;

public class GoodByePacketHandler implements PacketHandler<GoodByePacket> {

    private static final Logger logger = LoggerFactory.getLogger(GoodByePacketHandler.class);

    @Override
    public void handle(MapServer server, GoodByePacket packet) {
        logger.info("Got a GoodByePacket {}", packet);
    }
}
