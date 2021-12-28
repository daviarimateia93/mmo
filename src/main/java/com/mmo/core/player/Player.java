package com.mmo.core.player;

import java.util.UUID;

import com.mmo.core.animate.Animate;
import com.mmo.core.attribute.Attributes;
import com.mmo.core.map.Position;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.stat.Stats;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Player extends Animate {

    private final UUID instanceId;
    private final String name;
    private final Position position;
    private final Stats stats;
    private final Attributes attributes;

    @Builder
    private Player(
            @NonNull UUID instanceId,
            @NonNull String name,
            @NonNull Position position,
            @NonNull Stats stats,
            @NonNull Attributes attributes) {

        this.instanceId = instanceId;
        this.name = name;
        this.position = position;
        this.stats = stats;
        this.attributes = attributes;
    }

    @Override
    public UUID getId() {
        return instanceId;
    }

    @Override
    protected void onDie() {
        super.onDie();

        dispatch(PlayerPersistPacket.builder()
                .source(instanceId)
                .player(this)
                .build());
    }
}
