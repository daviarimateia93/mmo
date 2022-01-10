package com.mmo.server.core.animate;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.game.GameRunnerMapMocker;
import com.mmo.server.core.looper.LooperContextMocker;
import com.mmo.server.core.map.Map;
import com.mmo.server.core.map.Position;
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

    @Test
    @Timeout(value = 10500, unit = TimeUnit.MILLISECONDS)
    public void attack() throws InterruptedException {
        AnimateImpl animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .y(15)
                        .z(10)
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
                        .y(25)
                        .z(10)
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

        LooperContextMocker.update(animate, 5000);

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(animate.isAttacking(), equalTo(false));
        assertThat(target.died, equalTo(true));

        verify(map).dispatch(AnimateDiePacket.builder()
                .source(target.getInstanceId())
                .killedBy(animate.getInstanceId())
                .build());
    }

    @Test
    @Timeout(value = 2500, unit = TimeUnit.MILLISECONDS)
    public void move() throws InterruptedException {
        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10)
                        .y(15)
                        .z(10)
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
                .x(20)
                .y(25)
                .z(15)
                .build();

        animate.move(expected);

        assertThat(animate.getTargetAnimate().isEmpty(), equalTo(true));
        assertThat(animate.isMoving(), equalTo(true));

        LooperContextMocker.update(animate, 2000);

        Position result = animate.getPosition();

        assertThat(animate.isMoving(), equalTo(false));
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
        protected void onMove(long distanceX, long distanceY, long distanceZ) {
            super.onMove(distanceX, distanceY, distanceZ);
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
