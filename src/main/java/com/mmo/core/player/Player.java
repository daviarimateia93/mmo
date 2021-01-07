package com.mmo.core.player;

import java.util.UUID;

import com.mmo.core.animate.Animate;
import com.mmo.core.attribute.Attributes;
import com.mmo.core.map.Position;
import com.mmo.core.stat.Stats;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Player extends Animate {

    private final UUID instanceId = UUID.randomUUID();
    private final String name;
    private final Position position;
    private final Stats stats;
    private final Attributes attributes;

    @Builder
    private Player(
            @NonNull String name,
            @NonNull Position position,
            @NonNull Stats stats,
            @NonNull Attributes attributes) {

        this.name = name;
        this.position = position;
        this.stats = stats;
        this.attributes = attributes;
    }
}
