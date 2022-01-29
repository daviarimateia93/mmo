package com.mmo.server.infrastructure.player;

import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.player.Player;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.infrastructure.mongo.MongoFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class MongoPlayerRepository implements PlayerRepository {

    private final MongoCollection<PlayerDTO> collection;

    public MongoPlayerRepository() {
        collection = MongoFactory.getInstance().getCollection("Player", PlayerDTO.class);
    }

    @Override
    public Optional<Player> find(UUID id) {
        PlayerDTO entity = collection.find(Filters.eq("_id", id)).first();

        return Optional.ofNullable(entity)
                .map(PlayerDTO::toPlayer);
    }

    @Override
    public void persist(Player player) {
        PlayerDTO entity = PlayerDTO.of(player);

        collection.replaceOne(
                Filters.eq("_id", entity.getId()),
                entity, new ReplaceOptions().upsert(true));
    }
}
