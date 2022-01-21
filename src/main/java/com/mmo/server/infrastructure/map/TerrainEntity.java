package com.mmo.server.infrastructure.map;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mmo.server.core.map.Terrain;
import com.mmo.server.infrastructure.math.RectangleEntity;

import lombok.Data;

@Data
public class TerrainEntity {

    private List<Float> heightMap = new ArrayList<>();
    private Set<RectangleEntity> forbiddenAreas = new LinkedHashSet<>();

    public Terrain toTerrain() {
        return Terrain.builder()
                .heightMap(heightMap)
                .forbiddenAreas(forbiddenAreas.stream()
                        .map(RectangleEntity::toRectangle)
                        .collect(Collectors.toList()))
                .build();
    }

    public static TerrainEntity of(Terrain terrain) {
        TerrainEntity entity = new TerrainEntity();
        entity.setHeightMap(terrain.getHeightMap());

        terrain.getForbiddenAreas()
                .stream()
                .map(RectangleEntity::of)
                .forEach(entity.getForbiddenAreas()::add);

        return entity;
    }
}
