package com.mmo.core.animate;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.mmo.core.attribute.Attributes;
import com.mmo.core.looper.LooperContextMocker;
import com.mmo.core.map.Position;

public class AnimateTest {

    @Test
    @Timeout(value = 10500, unit = TimeUnit.MILLISECONDS)
    public void attack() throws InterruptedException {
        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10L)
                        .y(15L)
                        .z(10L)
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

        Animate target = new AnimateImpl(
                Position.builder()
                        .x(20L)
                        .y(25L)
                        .z(10L)
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

        LooperContextMocker.update(animate, 3000);

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(animate.isAttacking(), equalTo(true));

        LooperContextMocker.update(animate, 5000);

        assertThat(animate.isMoving(), equalTo(false));
        assertThat(animate.isAttacking(), equalTo(false));
    }

    @Test
    @Timeout(value = 2500, unit = TimeUnit.MILLISECONDS)
    public void move() throws InterruptedException {
        Animate animate = new AnimateImpl(
                Position.builder()
                        .x(10L)
                        .y(15L)
                        .z(10L)
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
                .x(20L)
                .y(25L)
                .z(15L)
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
        UUID instanceId = UUID.randomUUID();
        String name = UUID.randomUUID().toString();
        Position position;
        Attributes attributes;

        AnimateImpl(Position position, Attributes attributes) {
            this.position = position;
            this.attributes = attributes;
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
    }
}
