package com.mmo.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.core.looper.LooperContext;

public class MapTest {

    @Test
    public void addEntity() {
        Entity entityA = new Entity(Position.builder()
                .x(10L)
                .y(15L)
                .z(10L)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11L)
                .y(13L)
                .z(10L)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(9L)
                .y(17L)
                .z(10L)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .build();

        map.addEntity(entityA);
        map.addEntity(entityB);
        map.addEntity(entityC);

        MapEntity[] expected = { entityA, entityB, entityC };
        Collection<MapEntity> result = map.getEntities();

        assertThat(result, containsInAnyOrder(expected));
        assertThat(result.size(), equalTo(expected.length));
        assertThat(map.getEntity(entityA.getInstanceId()), equalTo(entityA));
        assertThat(map.getEntity(entityA.getInstanceId(), Entity.class), equalTo(entityA));
    }

    @Test
    public void removeEntity() {
        Entity entityA = new Entity(Position.builder()
                .x(10L)
                .y(15L)
                .z(10L)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11L)
                .y(13L)
                .z(10L)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(12L)
                .y(16L)
                .z(12L)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .build();

        map.addEntity(entityA);
        map.addEntity(entityB);
        map.addEntity(entityC);
        map.removeEntity(entityA);
        map.removeEntity(entityC.getInstanceId());

        MapEntity[] expected = { entityB };
        Collection<MapEntity> result = map.getEntities();

        assertThat(result, containsInAnyOrder(expected));
        assertThat(result.size(), equalTo(expected.length));
        assertThat(map.findEntity(entityA.getInstanceId()), equalTo(Optional.empty()));
        assertThat(map.findEntity(entityA.getInstanceId(), Entity.class), equalTo(Optional.empty()));
        assertThat(map.findEntity(entityC.getInstanceId()), equalTo(Optional.empty()));
        assertThat(map.findEntity(entityC.getInstanceId(), Entity.class), equalTo(Optional.empty()));
    }

    @Test
    public void getNearbyEntities() {
        Entity entityA = new Entity(Position.builder()
                .x(10L)
                .y(15L)
                .z(10L)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11L)
                .y(13L)
                .z(10L)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(19L)
                .y(27L)
                .z(10L)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .build();

        map.addEntity(entityA);
        map.addEntity(entityB);
        map.addEntity(entityC);

        MapEntity[] expected = { entityA, entityB };
        Set<MapEntity> result = map.getNearbyEntities(entityA);

        assertThat(result, containsInAnyOrder(expected));
        assertThat(result.size(), equalTo(expected.length));
    }

    @Test
    public void getTypedNearbyEntities() {
        Entity entityA = new Entity(Position.builder()
                .x(10L)
                .y(15L)
                .z(10L)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11L)
                .y(13L)
                .z(10L)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(19L)
                .y(27L)
                .z(10L)
                .build());

        SubEntity entityD = new SubEntity(Position.builder()
                .x(11L)
                .y(13L)
                .z(10L)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .build();

        map.addEntity(entityA);
        map.addEntity(entityB);
        map.addEntity(entityC);
        map.addEntity(entityD);

        MapEntity[] expected = { entityD };
        Set<SubEntity> result = map.getNearbyEntities(entityA, SubEntity.class);

        assertThat(result, containsInAnyOrder(expected));
        assertThat(result.size(), equalTo(expected.length));
    }

    private class Entity implements MapEntity {

        UUID instanceId = UUID.randomUUID();
        String name = UUID.randomUUID().toString();
        Position position;

        public Entity(Position position) {
            this.position = position;
        }

        @Override
        public UUID getInstanceId() {
            return instanceId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public void update(LooperContext context) {

        }
    }

    private class SubEntity extends Entity {

        public SubEntity(Position position) {
            super(position);
        }
    }
}
