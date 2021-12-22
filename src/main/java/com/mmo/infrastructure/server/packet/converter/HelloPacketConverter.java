package com.mmo.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.core.packet.HelloPacket;
import com.mmo.infrastructure.server.packet.PacketConverter;
import com.mmo.infrastructure.server.packet.PacketReader;
import com.mmo.infrastructure.server.packet.PacketWriter;

public class HelloPacketConverter implements PacketConverter<HelloPacket> {

    @Override
    public HelloPacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return HelloPacket.builder()
                    .source(source)
                    .build();
        }
    }

    @Override
    public byte[] toBytes(HelloPacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            return writer.toBytes();
        }
    }
}
