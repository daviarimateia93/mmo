package com.mmo.infrastructure.map.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.infrastructure.map.packet.HelloPacket;
import com.mmo.infrastructure.map.server.MapServer;

public class HelloPacketHandler implements PacketHandler<HelloPacket> {

    private static final Logger logger = LoggerFactory.getLogger(HelloPacketHandler.class);

    @Override
    public void handle(MapServer server, HelloPacket packet) {
        logger.info("Got a HelloPacket {}", packet);
    }
}
