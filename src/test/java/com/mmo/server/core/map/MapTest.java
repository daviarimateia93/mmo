package com.mmo.server.core.map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.looper.LooperContext;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.infrastructure.server.TestPacket;

import lombok.Data;

public class MapTest {

    @Test
    public void addEntity() {
        Entity entityA = new Entity(Position.builder()
                .x(10)
                .y(15)
                .z(10)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11)
                .y(13)
                .z(10)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(9)
                .y(17)
                .z(10)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
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
                .x(10)
                .y(15)
                .z(10)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11)
                .y(13)
                .z(10)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(12)
                .y(16)
                .z(12)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
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
                .x(10)
                .y(15)
                .z(10)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11)
                .y(13)
                .z(10)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(19)
                .y(27)
                .z(10)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
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
                .x(10)
                .y(15)
                .z(10)
                .build());

        Entity entityB = new Entity(Position.builder()
                .x(11)
                .y(13)
                .z(10)
                .build());

        Entity entityC = new Entity(Position.builder()
                .x(19)
                .y(27)
                .z(10)
                .build());

        SubEntity entityD = new SubEntity(Position.builder()
                .x(11)
                .y(13)
                .z(10)
                .build());

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
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

    @Test
    public void sendPacket() {
        PacketDispatchSubscriber packetSubscriber = new PacketDispatchSubscriber();

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .packetSubscribers(Set.of(packetSubscriber))
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
                .build();

        TestPacket expected = TestPacket.builder()
                .source(UUID.randomUUID())
                .property1("property1")
                .property2(87)
                .build();

        map.dispatch(expected);

        assertThat(packetSubscriber.getPacket(), is(expected));
        assertThat(packetSubscriber.getTarget(), is(Optional.empty()));
    }

    @Test
    public void sendPacketWithTarget() {
        PacketDispatchSubscriber packetSubscriber = new PacketDispatchSubscriber();

        Map map = Map.builder()
                .name("name")
                .description("description")
                .nearbyRatio(5)
                .packetSubscribers(Set.of(packetSubscriber))
                .terrain(Terrain.builder()
                        .heightMap(List.of(128.f, 128.f, 128.f, 129.f, 130.f, 131.f))
                        .build())
                .build();

        TestPacket expectedPacket = TestPacket.builder()
                .source(UUID.randomUUID())
                .property1("property1")
                .property2(87)
                .build();

        UUID expectedTarget = UUID.randomUUID();

        map.dispatch(expectedPacket, expectedTarget);

        assertThat(packetSubscriber.getPacket(), is(expectedPacket));
        assertThat(packetSubscriber.getTarget(), is(Optional.of(expectedTarget)));
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

    @Data
    private class PacketDispatchSubscriber implements MapPacketDispatchSubscriber {

        Packet packet;
        Optional<UUID> target;

        @Override
        public void onDispatch(Packet packet, Optional<UUID> target) {
            this.packet = packet;
            this.target = target;
        }
    }
}
