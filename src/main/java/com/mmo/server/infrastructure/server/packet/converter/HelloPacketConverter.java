package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.HelloPacket;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketReaderConverter;
import com.mmo.server.infrastructure.server.packet.PacketWriter;
import com.mmo.server.infrastructure.server.packet.PacketWriterConverter;

public class HelloPacketConverter implements PacketReaderConverter<HelloPacket>, PacketWriterConverter<HelloPacket> {

    @Override
    public HelloPacket fromBytes(UUID source, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            return HelloPacket.builder()
                    .source(source)
                    .userName(reader.readUTF())
                    .userPassword(reader.readUTF())
                    .build();
        }
    }

    @Override
    public byte[] toBytes(HelloPacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            writer.writeUTF(packet.getUserName());
            writer.writeUTF(packet.getUserPassword());
            return writer.toBytes();
        }
    }
}
