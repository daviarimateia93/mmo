package com.mmo.server.core.animate;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.game.GameRunnerMapMocker;
import com.mmo.server.core.looper.LooperContextMocker;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.math.Vertex;
import com.mmo.server.core.packet.AnimateDiePacket;

public class AnimateTest {

    private static Map map;

    @BeforeAll
    private static void setup() {
        map = GameRunnerMapMocker.run();
    }

    @AfterAll
    private static void clear() {
        GameRunnerMapMocker.stop();
    }

    @AfterEach
    private void clearEach() {
        when(map.getTerrain().isInsideForbiddenArea(anyInt(), anyInt())).thenReturn(false);
    }

    @Test
    @Timeout(value = 11000, unit = TimeUnit.MILLISECONDS)
    public void attack() throws InterruptedException {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(60)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(50)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        AnimateImpl target = new AnimateImpl(
                Position.builder()
                        .x(20)
                        .z(25)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(50)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        animate.attack(target);

        assertThat(animate.isAttacking(), equalTo(true));

        LooperContextMocker.update(animate, 1000);

        assertThat(animate.isMoving(), equalTo(true));
        assertThat(animate.isAttacking(), equalTo(true));
        assertThat(animate.moved, equalTo(true));

        LooperContextMocker.update(animate, 3000);

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(animate.isAttacking(), equalTo(true));
        assertThat(animate.attacked, equalTo(true));
        assertThat(target.damaged, equalTo(true));

        LooperContextMocker.update(animate, 6000);

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(animate.isAttacking(), equalTo(false));
        assertThat(target.died, equalTo(true));

        verify(map).dispatch(AnimateDiePacket.builder()
                .source(target.getInstanceId())
                .killedBy(animate.getInstanceId())
                .build());
    }

    @Test
    @Timeout(value = 3500, unit = TimeUnit.MILLISECONDS)
    public void move() throws InterruptedException {
        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        Position expected = Position.builder()
                .x(13)
                .z(25)
                .build();

        animate.move(expected);

        assertThat(animate.getTargetAnimate().isEmpty(), equalTo(true));
        assertThat(animate.isMoving(), equalTo(true));

        LooperContextMocker.update(animate, 3000);

        Position result = animate.getPosition();

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(result, equalTo(expected));
    }

    @Test
    @Timeout(value = 3500, unit = TimeUnit.MILLISECONDS)
    public void moveStraightly() throws InterruptedException {
        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        Position expected = Position.builder()
                .x(30)
                .z(15)
                .build();

        animate.move(expected);

        assertThat(animate.getTargetAnimate().isEmpty(), equalTo(true));
        assertThat(animate.isMoving(), equalTo(true));

        LooperContextMocker.update(animate, 3000);

        Position result = animate.getPosition();

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(result, equalTo(expected));
    }

    @Test
    @Timeout(value = 2500, unit = TimeUnit.MILLISECONDS)
    public void stopMovingWhenCollision() throws InterruptedException {
        when(map.getTerrain().isInsideForbiddenArea(19, 15)).thenReturn(true);

        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        Position expected = Position.builder()
                .x(18)
                .z(15)
                .build();

        animate.move(Position.builder()
                .x(20)
                .z(15)
                .build());

        assertThat(animate.getTargetAnimate().isEmpty(), equalTo(true));
        assertThat(animate.isMoving(), equalTo(true));

        LooperContextMocker.update(animate, 2000);

        Position result = animate.getPosition();

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(result, equalTo(expected));
    }

    @Test
    @Timeout(value = 1500, unit = TimeUnit.MILLISECONDS)
    public void stopMoving() {
        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        Position expected = Position.builder()
                .x(18)
                .z(15)
                .build();

        animate.move(Position.builder()
                .x(20)
                .z(15)
                .build());

        assertThat(animate.getTargetAnimate().isEmpty(), equalTo(true));
        assertThat(animate.isMoving(), equalTo(true));

        LooperContextMocker.update(animate, 650);

        animate.stopMoving();

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(animate.getPosition(), not(equalTo(expected)));
    }

    @Test
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    public void stopAttacking() {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(60)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(50)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        AnimateImpl target = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(42)
                        .defense(50)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(38)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        animate.attack(target);

        assertThat(animate.isAttacking(), equalTo(true));

        LooperContextMocker.update(animate, 2100);

        animate.stopAttacking();

        assertThat(animate.isAttacking(), equalTo(false));
        assertThat(target.damaged, equalTo(true));
        assertThat(target.isAlive(), equalTo(true));
    }

    @Test
    public void getAttackUpdateRateInMillis() {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(60)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(50)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        int expected = 2000;
        int result = animate.getAttackUpdateRateInMillis();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getMoveUpdateRateInMillis() {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(60)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(50)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        int expected = 300;
        int result = animate.getMoveUpdateRateInMillis();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getMoveDistance() {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(60)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(50)
                        .moveSpeed(4)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        Vertex expectedWithoutRemainder = new Vertex(2, 1);
        Vertex resultWithoutRemainder = animate.getMoveDistance(
                animate.getPosition().getX(),
                animate.getPosition().getZ(),
                30, 25);

        assertThat(resultWithoutRemainder, equalTo(expectedWithoutRemainder));

        Vertex expectedWithRemainder = new Vertex(3, 1);
        Vertex resultWithRemainderResult = animate.getMoveDistance(
                animate.getPosition().getX(),
                animate.getPosition().getZ(),
                30,
                25);

        assertThat(resultWithRemainderResult, equalTo(expectedWithRemainder));
    }

    @Test
    public void getMoveDistanceStraightly() {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .z(15)
                        .build(),
                Attributes.builder()
                        .hp(30)
                        .mp(31)
                        .attack(60)
                        .defense(33)
                        .magicDefense(34)
                        .hitRate(35)
                        .critical(36)
                        .dodgeRate(37)
                        .attackSpeed(50)
                        .moveSpeed(2)
                        .hpRecovery(40)
                        .mpRecovery(41)
                        .attackRange(3)
                        .build());

        Vertex expected = new Vertex(2, 0);
        Vertex result = animate.getMoveDistance(animate.getPosition().getX(), animate.getPosition().getZ(), 30, 15);

        assertThat(result, equalTo(expected));
    }

    private class AnimateImpl extends Animate {
        UUID id = UUID.randomUUID();
        UUID instanceId = UUID.randomUUID();
        String name = UUID.randomUUID().toString();
        Position position;
        Attributes attributes;
        boolean moved = false;
        boolean damaged = false;
        boolean attacked = false;
        boolean died = false;

        AnimateImpl(Position position, Attributes attributes) {
            this.position = position;
            this.attributes = attributes;
        }

        @Override
        public UUID getId() {
            return id;
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
        public Attributes getAttributes() {
            return attributes;
        }

        @Override
        protected void onMove(int distanceX, int distanceZ) {
            super.onMove(distanceX, distanceZ);
            moved = true;
        }

        @Override
        protected void onDamage(int damage, Animate source) {
            super.onDamage(damage, source);
            damaged = true;
        }

        @Override
        protected void onAttack(int damage, Animate target) {
            super.onAttack(damage, target);
            attacked = true;
        }

        @Override
        protected void onDie(Animate source) {
            super.onDie(source);
            died = true;
        }
    }
}
