package com.mmo.infrastructure.player;

import java.util.Optional;
import java.util.UUID;

import com.mmo.core.player.Player;
import com.mmo.core.player.PlayerRepository;
import com.mmo.infrastructure.mongo.MongoFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class MongoPlayerRepository implements PlayerRepository {

    private final MongoCollection<PlayerEntity> collection;

    public MongoPlayerRepository() {
        collection = MongoFactory.getInstance().getCollection("Player", PlayerEntity.class);
    }

    @Override
    public Optional<Player> find(UUID id) {
        PlayerEntity entity = collection.find(Filters.eq("_id", id)).first();

        return Optional.ofNullable(entity)
                .map(PlayerEntity::toPlayer);
    }

    @Override
    public void persist(Player player) {
        PlayerEntity entity = PlayerEntity.of(player);

        collection.replaceOne(
                Filters.eq("_id", entity.getId()),
                entity, new ReplaceOptions().upsert(true));
    }
}
