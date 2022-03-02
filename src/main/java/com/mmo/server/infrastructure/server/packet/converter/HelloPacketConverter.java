package com.mmo.server.infrastructure.server.packet.converter;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class HelloPacketConverter implements PacketConverter<HelloPacket> {

    @Override
    public HelloPacket read(UUID source, OffsetDateTime creation, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return HelloPacket.builder()
                    .source(source)
                    .userName(reader.readUTF())
                    .userPassword(reader.readUTF())
                    .build();
        }
    }

    @Override
    public byte[] write(HelloPacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUTF(packet.getUserName());
            writer.writeUTF(packet.getUserPassword());
            return writer.toBytes();
        }
    }
}
