package com.mmo.core.player;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {

    Optional<Player> find(UUID id);

    void persist(Player player);
}
