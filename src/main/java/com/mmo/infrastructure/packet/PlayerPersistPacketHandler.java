package com.mmo.infrastructure.packet;

import java.util.Optional;
import java.util.UUID;

import com.mmo.core.map.Map;
import com.mmo.core.packet.PacketHandler;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.player.Player;
import com.mmo.core.player.PlayerRepository;
import com.mmo.infrastructure.mongo.MongoFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class PlayerPersistPacketHandler implements PlayerRepository, PacketHandler<PlayerPersistPacket> {

    private final MongoCollection<Player> collection;

    public PlayerPersistPacketHandler() {
        collection = MongoFactory.getInstance().getCollection(Player.class);
    }

    @Override
    public Optional<Player> find(UUID id) {
        return Optional.ofNullable(collection.find(Filters.eq("_id", id)).first());
    }

    @Override
    public void persist(Player player) {
        collection.replaceOne(Filters.eq("_id", player.getId()), player, new ReplaceOptions().upsert(true));
    }

    @Override
    public void handle(Map map, PlayerPersistPacket packet) {
        persist(packet.getPlayer());
    }
}
