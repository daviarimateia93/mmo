package com.mmo.infrastructure.mongo;

import java.util.Objects;

import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mmo.infrastructure.config.ConfigProvider;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MongoFactory {

    private static final String CONFIG_MONGO_FACTORY_CONNECTION_URI = "mongo.factory.connection.uri";
    private static final String CONFIG_MONGO_FACTORY_DATABASE = "mongo.factory.database";

    private static MongoFactory instance;

    private final MongoClient client;
    private final MongoDatabase database;

    public static MongoFactory getInstance() {
        if (Objects.isNull(instance)) {
            instance = new MongoFactory();
        }

        return instance;
    }

    private MongoFactory() {
        String connectionUri = ConfigProvider.getInstance().getString(CONFIG_MONGO_FACTORY_CONNECTION_URI);

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .automatic(true)
                        .register(UuidCodecProvider.class)
                        .build()));

        client = MongoClients.create(MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(codecRegistry)
                .applyConnectionString(new ConnectionString(connectionUri))
                .build());

        database = client.getDatabase(ConfigProvider.getInstance().getString(CONFIG_MONGO_FACTORY_DATABASE));
    }

    public <T> MongoCollection<T> getCollection(String name, Class<T> type) {
        return database.getCollection(name, type);
    }
}
