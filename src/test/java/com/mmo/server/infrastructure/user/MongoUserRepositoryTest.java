package com.mmo.server.infrastructure.user;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mmo.server.core.user.User;
import com.mmo.server.infrastructure.mongo.MongoServer;

public class MongoUserRepositoryTest {

    private static MongoUserRepository repository;

    @BeforeAll
    public static void setup() {
        MongoServer.getInstance().start();

        repository = new MongoUserRepository();
    }

    @Test
    public void findsAndPersist() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("name " + UUID.randomUUID())
                .password("password")
                .build();

        assertThat(repository.find(user.getId()), is(Optional.empty()));
        assertThat(repository.findByName(user.getName()), is(Optional.empty()));

        repository.persist(user);

        assertThat(repository.find(user.getId()), is(Optional.of(user)));
        assertThat(repository.findByName(user.getName()), is(Optional.of(user)));
    }
}
