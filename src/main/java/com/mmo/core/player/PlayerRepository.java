package com.mmo.core.player;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {

    Optional<Player> findByInstanceId(UUID instanceId);

    void save(Player player);
}
