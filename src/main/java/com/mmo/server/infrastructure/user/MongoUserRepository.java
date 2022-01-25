package com.mmo.server.infrastructure.user;

import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.user.User;
import com.mmo.server.core.user.UserRepository;
import com.mmo.server.infrastructure.mongo.MongoFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class MongoUserRepository implements UserRepository {

    private final MongoCollection<UserEntity> collection;

    public MongoUserRepository() {
        collection = MongoFactory.getInstance().getCollection("User", UserEntity.class);
    }

    @Override
    public Optional<User> find(UUID id) {
        UserEntity entity = collection.find(Filters.eq("_id", id)).first();

        return Optional.ofNullable(entity)
                .map(UserEntity::toUser);
    }

    @Override
    public void persist(User player) {
        UserEntity entity = UserEntity.of(player);

        collection.replaceOne(
                Filters.eq("_id", entity.getId()),
                entity, new ReplaceOptions().upsert(true));
    }
}
