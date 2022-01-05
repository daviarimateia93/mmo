package com.mmo.server.core.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Terrain {

    private final List<Float> heightMap = new ArrayList<>();
    private final Set<Rectangle> forbiddenAreas = new HashSet<>();

    @Builder
    private Terrain(
            @NonNull Collection<Float> heightMap,
            Collection<Rectangle> forbiddenAreas) {

        this.heightMap.addAll(heightMap);

        if (Objects.nonNull(forbiddenAreas)) {
            this.forbiddenAreas.addAll(forbiddenAreas);
        }
    }

    public List<Float> getHeightMap() {
        return Collections.unmodifiableList(heightMap);
    }

    public Set<Rectangle> getForbiddenAreas() {
        return Collections.unmodifiableSet(forbiddenAreas);
    }

    public boolean isInsideForbiddenArea(Position position) {
        return forbiddenAreas.stream()
                .anyMatch(rectangle -> rectangle.intersects(position));
    }
}
