package com.mmo.server.infrastructure.map;

import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.MapRepository;
import com.mmo.server.infrastructure.mongo.MongoFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class MongoMapRepository implements MapRepository {

    private final MongoCollection<MapEntity> collection;

    public MongoMapRepository() {
        collection = MongoFactory.getInstance().getCollection("Map", MapEntity.class);
    }

    @Override
    public Optional<Map> find(UUID id) {
        MapEntity entity = collection.find(Filters.eq("_id", id)).first();

        return Optional.ofNullable(entity)
                .map(MapEntity::toMap);
    }

    @Override
    public void persist(Map player) {
        MapEntity entity = MapEntity.of(player);

        collection.replaceOne(
                Filters.eq("_id", entity.getId()),
                entity, new ReplaceOptions().upsert(true));
    }
}
