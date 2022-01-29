package com.mmo.server.infrastructure.map;

import java.util.UUID;

import com.mmo.server.core.map.Map;

import lombok.Data;

@Data
public class MapDTO {

    private UUID id;
    private String name;
    private String description;
    private Integer nearbyRatio;
    private TerrainDTO terrain;

    public static MapDTO of(Map map) {
        MapDTO dto = new MapDTO();
        dto.setId(map.getId());
        dto.setName(map.getName());
        dto.setDescription(map.getDescription());
        dto.setNearbyRatio(map.getNearbyRatio());
        dto.setTerrain(TerrainDTO.of(map.getTerrain()));

        return dto;
    }

    public Map toMap() {
        return Map.builder()
                .id(id)
                .name(name)
                .description(description)
                .nearbyRatio(nearbyRatio)
                .terrain(terrain.toTerrain())
                .build();
    }
}
