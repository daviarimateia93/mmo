package com.mmo.server.core.map;

import java.util.Optional;
import java.util.UUID;

public interface MapRepository {

    Optional<Map> find(UUID id);

    void persist(Map map);
}
