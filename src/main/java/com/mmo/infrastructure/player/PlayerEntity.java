package com.mmo.infrastructure.player;

import java.util.UUID;

import com.mmo.core.player.Player;
import com.mmo.infrastructure.animate.AttributesEntity;
import com.mmo.infrastructure.animate.StatsEntity;
import com.mmo.infrastructure.map.PositionEntity;

import lombok.Data;

@Data
public class PlayerEntity {

    private UUID id;
    private UUID instanceId;
    private String name;
    private PositionEntity position;
    private StatsEntity stats;
    private AttributesEntity attributes;

    public Player toPlayer() {
        return Player.builder()
                .instanceId(getInstanceId())
                .name(getName())
                .position(getPosition().toPosition())
                .stats(getStats().toStats())
                .attributes(getAttributes().toAttributes())
                .build();
    }

    public static PlayerEntity of(Player player) {
        PlayerEntity entity = new PlayerEntity();
        entity.setId(player.getId());
        entity.setInstanceId(player.getInstanceId());
        entity.setName(player.getName());
        entity.setPosition(PositionEntity.of(player.getPosition()));
        entity.setStats(StatsEntity.of(player.getStats()));
        entity.setAttributes(AttributesEntity.of(player.getAttributes()));

        return entity;
    }
}