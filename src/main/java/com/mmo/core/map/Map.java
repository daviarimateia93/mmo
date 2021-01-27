package com.mmo.core.map;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
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

    private final ConcurrentHashMap<UUID, MapEntity> entities = new ConcurrentHashMap<>();
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

    public <T extends MapEntity> T getEntity(UUID instanceId, Class<T> type) {
        return findEntity(instanceId, type)
                .orElseThrow(() -> new MapEntityNotFoundException("Entity not found with instanceId %s", instanceId));
    }

    public MapEntity getEntity(UUID instanceId) {
        return findEntity(instanceId)
                .orElseThrow(() -> new MapEntityNotFoundException("Entity not found with instanceId %s", instanceId));
    }

    @SuppressWarnings("unchecked")
    public <T extends MapEntity> Optional<T> findEntity(UUID instanceId, Class<T> type) {
        return findEntity(instanceId)
                .filter(entity -> type.isAssignableFrom(entity.getClass()))
                .map(entity -> (T) entity);
    }

    public Optional<MapEntity> findEntity(UUID instanceId) {
        return Optional.ofNullable(entities.get(instanceId));
    }

    public Collection<MapEntity> getEntities() {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public void update(LooperContext context) {
        entities.values().forEach(animate -> animate.update(context));
    }

    public void addEntity(MapEntity entity) {
        entities.put(entity.getInstanceId(), entity);
    }

    public void removeEntity(MapEntity entity) {
        entities.remove(entity.getInstanceId());
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
        return entities.values().stream()
                .filter(entity -> isNearby(baseEntity, entity))
                .collect(Collectors.toSet());
    }

    private boolean isNearby(MapEntity baseEntity, MapEntity testingEntity) {
        return baseEntity.getPosition().isNearby(testingEntity.getPosition(), nearbyRatio);
    }
}
