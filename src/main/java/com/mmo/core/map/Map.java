package com.mmo.core.map;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.mmo.core.looper.LooperContext;
import com.mmo.core.looper.LooperUpdater;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Map implements LooperUpdater {

    private final Set<MapEntity> entities = new HashSet<>();
    private final String name;
    private final String description;
    private final Integer nearbyRatio;

    @Builder
    private Map(
            @NonNull String name,
            @NonNull String description,
            @NonNull Integer nearbyRatio) {

        this.name = name;
        this.description = description;
        this.nearbyRatio = nearbyRatio;
    }

    public Set<MapEntity> getEntities() {
        return Collections.unmodifiableSet(entities);
    }

    @Override
    public void update(LooperContext context) {
        entities.forEach(animate -> animate.update(context));
    }

    public void addEntity(MapEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(MapEntity entity) {
        entities.remove(entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends MapEntity> Set<T> getNearbyEntities(MapEntity baseEntity, Class<T> type) {
        return getNearbyEntities(baseEntity)
                .stream()
                .filter(entity -> type.isAssignableFrom(entity.getClass()))
                .map(entity -> (T) entity)
                .collect(Collectors.toSet());
    }

    public Set<MapEntity> getNearbyEntities(MapEntity baseEntity) {
        return entities.stream()
                .filter(entity -> isNearby(baseEntity, entity))
                .collect(Collectors.toSet());
    }

    private boolean isNearby(MapEntity baseEntity, MapEntity testingEntity) {
        return baseEntity.getPosition().isNearby(testingEntity.getPosition(), nearbyRatio);
    }
}
