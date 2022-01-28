package com.mmo.server.infrastructure.mongo;

import java.util.List;
import java.util.Objects;

import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mmo.server.infrastructure.config.ConfigProvider;
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

    private final ConfigProvider configProvider;
    private final MongoClient client;
    private final MongoDatabase database;

    public static MongoFactory getInstance() {
        if (Objects.isNull(instance)) {
            instance = new MongoFactory();
        }

        return instance;
    }

    private MongoFactory() {
        configProvider = ConfigProvider.getInstance();

        String connectionUri = configProvider.getString(CONFIG_MONGO_FACTORY_CONNECTION_URI);

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .automatic(true)
                        .register(UuidCodecProvider.class)
                        .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                        .build()));

        client = MongoClients.create(MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(codecRegistry)
                .applyConnectionString(new ConnectionString(connectionUri))
                .build());

        database = client.getDatabase(configProvider.getString(CONFIG_MONGO_FACTORY_DATABASE));
    }

    public <T> MongoCollection<T> getCollection(String name, Class<T> type) {
        return database.getCollection(name, type);
    }
}
