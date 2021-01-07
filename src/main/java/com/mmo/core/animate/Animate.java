package com.mmo.core.animate;

import java.util.Objects;
import java.util.Optional;

import com.mmo.core.attribute.Attribute;
import com.mmo.core.attribute.AttributeModifier;
import com.mmo.core.attribute.Attributes;
import com.mmo.core.looper.LooperContext;
import com.mmo.core.map.MapEntity;
import com.mmo.core.map.Position;
import com.mmo.core.property.PropertyModifierAction;

public abstract class Animate implements MapEntity {

    private static final int MOVE_UPDATE_RATIO_IN_MILLIS = 300;

    private Long lastAttackStartTime;
    private Long lastMoveStartTime;
    private Animate targetAnimate;
    private Position targetPosition;

    public abstract Attributes getAttributes();

    public boolean isAttacking() {
        return Objects.nonNull(lastAttackStartTime);
    }

    public boolean isMoving() {
        return Objects.nonNull(lastMoveStartTime);
    }

    public boolean isAlive() {
        return getAttributes().getFinalHP() > 0;
    }

    public boolean isInsideAttackRange(Position position) {
        int attackRange = getAttributes().getFinalAttackRange();

        return getPosition().isNearby(position, attackRange);
    }

    public Optional<Position> getTargetPosition() {
        Position position = getTargetAnimate()
                .map(Animate::getPosition)
                .orElse(targetPosition);

        return Optional.ofNullable(position);
    }

    public Optional<Animate> getTargetAnimate() {
        return Optional.ofNullable(targetAnimate);
    }

    public void attack(Animate target) {
        clearTargetPosition();
        targetAnimate = target;
        lastAttackStartTime = System.currentTimeMillis();
    }

    private void attack() {
        Animate target = getTargetAnimate().orElseThrow();
        int attack = getAttributes().getFinalAttack();
        int targetDefense = target.getAttributes().getFinalDefense();
        int damage = attack - targetDefense;

        target.getAttributes().modify(AttributeModifier.builder()
                .action(PropertyModifierAction.DECREMENT)
                .attribute(Attribute.HP)
                .value(damage)
                .build());

        if (!target.isAlive()) {
            lastAttackStartTime = null;
        }
    }

    public void move(Position target) {
        clearTargetAnimate();
        targetPosition = target;
        lastMoveStartTime = System.currentTimeMillis();
    }

    private void move() {
        Position current = getPosition();
        Position target = getTargetPosition().orElseThrow();

        long speed = getAttributes().getFinalMoveSpeed();

        if (current.getX() < target.getX()) {
            long difference = target.getX() - current.getX();
            long distance = speed > difference ? difference : speed;
            current.incrementX(distance);
        } else {
            long difference = current.getX() - target.getX();
            long distance = speed > difference ? difference : speed;
            current.decrementX(distance);
        }

        if (current.getY() < target.getY()) {
            long difference = target.getY() - current.getY();
            long distance = speed > difference ? difference : speed;
            current.incrementY(distance);
        } else {
            long difference = current.getY() - target.getY();
            long distance = speed > difference ? difference : speed;
            current.decrementY(distance);
        }

        if (hasFinishedMoving(current, target)) {
            lastMoveStartTime = null;
        }
    }

    private boolean hasFinishedMoving(Position current, Position target) {
        return current.getX().compareTo(target.getX()) == 0 && current.getY().compareTo(target.getY()) == 0;
    }

    @Override
    public void update(LooperContext context) {
        if (isAttacking()) {
            if (isInsideAttackRange(targetAnimate.getPosition())) {
                // we reach our target
                lastMoveStartTime = null;
            } else {
                // we should start moving to getting closer
                lastMoveStartTime = lastAttackStartTime;
            }

            update(context, lastAttackStartTime, getAttackUpdateRateInMillis(), this::attack);
        }

        if (isMoving()) {
            update(context, lastMoveStartTime, getMoveUpdateRateInMillis(), this::move);
        }
    }

    private void update(LooperContext context, long startTime, int updateRate, Runnable runnable) {
        long difference = context.getTick() - startTime;

        while (difference >= updateRate) {
            runnable.run();
            difference -= updateRate;
        }
    }

    /*
     * (attackSpeed / 100) by second
     * (attackSpeed / 100 / 1000) by millis
     * ---------------------------------
     * examples:
     * 025 -> 4s
     * 050 -> 2s
     * 100 -> 1s
     * 200 -> 0.5s
     * 400 -> 0.25s
     * attackSpeed * desiredSeconds = 100
     * desiredSeconds = 100/attackSpeed
     */
    private int getAttackUpdateRateInMillis() {
        return (100 / getAttributes().getFinalAttackSpeed()) * 1000;
    }

    private int getMoveUpdateRateInMillis() {
        return MOVE_UPDATE_RATIO_IN_MILLIS;
    }

    private void clearTargetPosition() {
        targetPosition = null;
    }

    private void clearTargetAnimate() {
        targetAnimate = null;
    }
}
