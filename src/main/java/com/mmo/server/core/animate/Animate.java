package com.mmo.server.core.animate;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmo.server.core.attribute.Attribute;
import com.mmo.server.core.attribute.AttributeModifier;
import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.game.Game;
import com.mmo.server.core.looper.LooperContext;
import com.mmo.server.core.map.MapEntity;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.AnimateDiePacket;
import com.mmo.server.core.packet.Packet;
import com.mmo.server.core.property.PropertyModifierAction;

public abstract class Animate implements MapEntity {

    private static final int MOVE_UPDATE_RATIO_IN_MILLIS = 300;

    private static final Logger logger = LoggerFactory.getLogger(Animate.class);

    private Long lastAttackStartTime;
    private Long lastMoveStartTime;
    private Animate targetAnimate;
    private Position targetPosition;

    public abstract UUID getId();

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

        target.onDamage(damage, this);

        onAttack(damage, target);

        if (!target.isAlive() && Objects.nonNull(lastAttackStartTime)) {
            lastAttackStartTime = null;
            target.onDie(this);
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

        long distanceX = moveX(current, target, speed);
        long distanceY = moveY(current, target, speed);
        long distanceZ = moveZ(current, target, speed);

        onMove(distanceX, distanceY, distanceZ);

        if (hasFinishedMoving(current, target) && Objects.nonNull(lastMoveStartTime)) {
            lastMoveStartTime = null;
        }
    }

    private long moveX(Position current, Position target, long speed) {
        long distanceX;

        if (current.getX() < target.getX()) {
            long difference = target.getX() - current.getX();
            distanceX = speed > difference ? difference : speed;
            current.incrementX(distanceX);
        } else {
            long difference = current.getX() - target.getX();
            distanceX = speed > difference ? difference : speed;
            current.decrementX(distanceX);
        }

        return distanceX;
    }

    private long moveY(Position current, Position target, long speed) {
        long distanceY;

        if (current.getY() < target.getY()) {
            long difference = target.getY() - current.getY();
            distanceY = speed > difference ? difference : speed;
            current.incrementY(distanceY);
        } else {
            long difference = current.getY() - target.getY();
            distanceY = speed > difference ? difference : speed;
            current.decrementY(distanceY);
        }

        return distanceY;
    }

    private long moveZ(Position current, Position target, long speed) {
        long distanceZ;

        if (current.getZ() < target.getZ()) {
            long difference = target.getZ() - current.getZ();
            distanceZ = speed > difference ? difference : speed;
            current.incrementZ(distanceZ);
        } else {
            long difference = current.getZ() - target.getZ();
            distanceZ = speed > difference ? difference : speed;
            current.decrementZ(distanceZ);
        }

        return distanceZ;
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

    protected void dispatch(Packet packet) {
        Game.getInstance().getMap().dispatch(packet);
    }

    protected void dispatch(Packet packet, UUID target) {
        Game.getInstance().getMap().dispatch(packet, target);
    }

    protected void onMove(long distanceX, long distanceY, long distanceZ) {
        logger.trace("Animate {} has moved to {}", getInstanceId(), getPosition());
    }

    protected void onDamage(int damage, Animate source) {
        logger.trace("Animate {} has suffered damage of {} by {}", getInstanceId(), damage, source.getInstanceId());
    }

    protected void onAttack(int damage, Animate target) {
        logger.trace("Animate {} has attacked {} with damage of {}", getInstanceId(), target.getInstanceId(), damage);
    }

    protected void onDie(Animate source) {
        logger.info("Animate {} has died by {}", getInstanceId(), source.getInstanceId());

        dispatch(AnimateDiePacket.builder()
                .source(getInstanceId())
                .killedBy(source.getInstanceId())
                .build());
    }
}
