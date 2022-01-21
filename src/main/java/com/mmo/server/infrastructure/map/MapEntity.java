package com.mmo.server.infrastructure.map;

import java.util.UUID;

import com.mmo.server.core.map.Map;

import lombok.Data;

@Data
public class MapEntity {

    private UUID id;
    private String name;
    private String description;
    private Integer nearbyRatio;
    private TerrainEntity terrain;

    public Map toMap() {
        return Map.builder()
                .id(id)
                .name(name)
                .description(description)
                .nearbyRatio(nearbyRatio)
                .terrain(terrain.toTerrain())
                .build();
    }

    public static MapEntity of(Map map) {
        MapEntity entity = new MapEntity();
        entity.setId(map.getId());
        entity.setName(map.getName());
        entity.setDescription(map.getDescription());
        entity.setNearbyRatio(map.getNearbyRatio());
        entity.setTerrain(TerrainEntity.of(map.getTerrain()));

        return entity;
    }
}
