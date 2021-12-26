package com.mmo.infrastructure.packet;

import com.mmo.core.map.Map;
import com.mmo.core.packet.PacketHandler;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.player.PlayerRepository;

public class PlayerPersistPacketHandler implements PacketHandler<PlayerPersistPacket> {

    private final PlayerRepository repository;

    public PlayerPersistPacketHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(Map map, PlayerPersistPacket packet) {
        repository.persist(packet.getPlayer());
    }
}
