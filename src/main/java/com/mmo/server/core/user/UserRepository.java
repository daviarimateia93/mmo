package com.mmo.server.core.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> find(UUID id);

    Optional<User> findByName(String name);

    void persist(User user);
}
