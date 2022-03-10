package com.mmo.server.infrastructure.player;

import java.util.UUID;

import com.mmo.server.core.player.Player;
import com.mmo.server.infrastructure.animate.AttributesDTO;
import com.mmo.server.infrastructure.animate.StatsDTO;
import com.mmo.server.infrastructure.map.PositionDTO;

import lombok.Data;

@Data
public class PlayerDTO {

    private UUID id;
    private UUID userId;
    private UUID instanceId;
    private String name;
    private PositionDTO position;
    private StatsDTO stats;
    private AttributesDTO attributes;

    public static PlayerDTO of(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setUserId(player.getUserId());
        dto.setInstanceId(player.getInstanceId());
        dto.setName(player.getName());
        dto.setPosition(PositionDTO.of(player.getPosition()));
        dto.setStats(StatsDTO.of(player.getStats()));
        dto.setAttributes(AttributesDTO.of(player.getAttributes()));

        return dto;
    }

    public Player toPlayer() {
        return Player.builder()
                .userId(getUserId())
                .instanceId(getInstanceId())
                .name(getName())
                .position(getPosition().toPosition())
                .stats(getStats().toStats())
                .attributes(getAttributes().toAttributes())
                .build();
    }
}