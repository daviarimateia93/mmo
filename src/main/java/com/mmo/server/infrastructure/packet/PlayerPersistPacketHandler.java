package com.mmo.server.infrastructure.packet;

import com.mmo.server.core.packet.PacketHandler;
import com.mmo.server.core.packet.PlayerPersistPacket;
import com.mmo.server.core.player.PlayerRepository;

public class PlayerPersistPacketHandler
        extends com.mmo.server.infrastructure.packet.PacketHandler
        implements PacketHandler<PlayerPersistPacket> {

    private final PlayerRepository repository;

    public PlayerPersistPacketHandler(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(PlayerPersistPacket packet) {
        repository.persist(packet.getPlayer());
    }
}
