package com.mmo.server.infrastructure.server.packet.converter;

import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.player.Player;
import com.mmo.server.infrastructure.server.packet.PacketWriter;
import com.mmo.server.infrastructure.server.packet.PacketWriterConverter;

public class PlayerUpdatePacketConverter implements PacketWriterConverter<PlayerUpdatePacket> {

    @Override
    public byte[] write(PlayerUpdatePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            write(writer, packet.getPlayer());
            return writer.toBytes();
        }
    }

    private void write(PacketWriter writer, Player player) {
        writer.writeUTF(player.getName());
        PositionConverter.write(writer, player.getPosition());
        StatsConverter.write(writer, player.getStats());
        AttributesConverter.write(writer, player.getAttributes());
    }
}