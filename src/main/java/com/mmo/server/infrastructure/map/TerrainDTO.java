package com.mmo.server.infrastructure.map;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mmo.server.core.map.Terrain;
import com.mmo.server.infrastructure.math.RectangleDTO;

import lombok.Data;

@Data
public class TerrainDTO {

    private List<Float> heightMap = new ArrayList<>();
    private Set<RectangleDTO> forbiddenAreas = new LinkedHashSet<>();

    public static TerrainDTO of(Terrain terrain) {
        TerrainDTO dto = new TerrainDTO();
        dto.setHeightMap(terrain.getHeightMap());

        terrain.getForbiddenAreas()
                .stream()
                .map(RectangleDTO::of)
                .forEach(dto.getForbiddenAreas()::add);

        return dto;
    }

    public Terrain toTerrain() {
        return Terrain.builder()
                .heightMap(heightMap)
                .forbiddenAreas(forbiddenAreas.stream()
                        .map(RectangleDTO::toRectangle)
                        .collect(Collectors.toList()))
                .build();
    }
}
