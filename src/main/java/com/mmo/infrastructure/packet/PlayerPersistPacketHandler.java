package com.mmo.infrastructure.packet;

import java.util.Optional;
import java.util.UUID;

import com.mmo.core.map.Map;
import com.mmo.core.packet.PacketHandler;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.player.Player;
import com.mmo.core.player.PlayerRepository;

public class PlayerPersistPacketHandler implements PlayerRepository, PacketHandler<PlayerPersistPacket> {

    @Override
    public Optional<Player> findByInstanceId(UUID instanceId) {
        return Optional.empty();
    }

    @Override
    public void persist(Player player) {

    }

    @Override
    public void handle(Map map, PlayerPersistPacket packet) {
        persist(packet.getPlayer());
    }
}
