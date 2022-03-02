package com.mmo.server.infrastructure.server.packet.converter;

import java.util.UUID;

import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.player.Player;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PlayerUpdatePacketConverter implements PacketConverter<PlayerUpdatePacket> {

    @Override
    public PlayerUpdatePacket read(UUID source, byte[] bytes) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] write(PlayerUpdatePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            Player player = packet.getPlayer();
            writer.writeUTF(player.getName());
            PositionConverter.write(writer, player.getPosition());
            StatsConverter.write(writer, player.getStats());
            AttributesConverter.write(writer, player.getAttributes());
            AnimateConverter.write(writer, player);
            return writer.toBytes();
        }
    }
}