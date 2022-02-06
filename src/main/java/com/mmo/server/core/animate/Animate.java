package com.mmo.server.core.animate;

import static java.lang.Math.*;

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
import com.mmo.server.core.math.Vertex;
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
    private boolean collided;
    private float moveDistanceXRemainder = 0;
    private float moveDistanceZRemainder = 0;

    public abstract UUID getId();

    public abstract Attributes getAttributes();

    public boolean isAttacking() {
        return Objects.nonNull(lastAttackStartTime);
    }

    public boolean isMoving() {
        return Objects.nonNull(lastMoveStartTime) && !hasCollided();
    }

    public boolean isAlive() {
        return getAttributes().getFinalHP() > 0;
    }

    public boolean isInsideAttackRange(Position position) {
        int attackRange = getAttributes().getFinalAttackRange();

        return getPosition().isNearby(position, attackRange);
    }

    public boolean hasCollided() {
        return collided;
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
    public int getAttackUpdateRateInMillis() {
        return (100 / getAttributes().getFinalAttackSpeed()) * 1000;
    }

    public int getMoveUpdateRateInMillis() {
        return MOVE_UPDATE_RATIO_IN_MILLIS;
    }

    public void attack(Animate target) {
        clearTargetPosition();
        targetAnimate = target;
        lastAttackStartTime = System.currentTimeMillis();
    }

    private void attack() {
        if (!isAttacking()) {
            return;
        }

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
            stopAttacking();
            target.onDie(this);
        } else {
            lastAttackStartTime = System.currentTimeMillis();
        }
    }

    public void move(Position target) {
        clearTargetAnimate();
        collided = false;
        targetPosition = target;
        lastMoveStartTime = System.currentTimeMillis();
    }

    private void move(Long lastMoveStartTime) {
        collided = false;
        this.lastMoveStartTime = lastMoveStartTime;
    }

    private void move() {
        if (!isMoving()) {
            return;
        }

        Position current = getPosition();
        Position target = getTargetPosition().orElseThrow();

        Vertex distance = getMoveDistance(current, target);
        int distanceX = moveX(current, target, distance.getX());
        int distanceZ = moveZ(current, target, distance.getZ());

        onMove(distanceX, distanceZ);

        if (hasFinishedMoving(current, target) && Objects.nonNull(lastMoveStartTime)) {
            stopMoving();
        } else {
            lastMoveStartTime = System.currentTimeMillis();
        }
    }

    public Vertex getMoveDistance(Position current, Position target) {
        int finalMoveSpeed = getAttributes().getFinalMoveSpeed();

        float targetOtherPointDistance = abs(target.getX() - current.getX());
        float playerOtherPointDistance = abs(current.getZ() - target.getZ());

        float playerTargetAngle = (float) toDegrees(atan(targetOtherPointDistance / playerOtherPointDistance));

        float xMoveDistance = (playerTargetAngle * finalMoveSpeed) / 90;
        float zMoveDistance = finalMoveSpeed - xMoveDistance;

        float xRemainder = xMoveDistance % 1;
        float zRemainder = zMoveDistance % 1;

        int xMoveDistanceAsInt = (int) (xMoveDistance - xRemainder);
        int zMoveDistanceAsInt = (int) (zMoveDistance - zRemainder);

        moveDistanceXRemainder += xRemainder;
        moveDistanceZRemainder += zRemainder;

        if (moveDistanceXRemainder >= 1) {
            xMoveDistanceAsInt++;
            moveDistanceXRemainder -= 1;
        }

        if (moveDistanceZRemainder >= 1) {
            zMoveDistanceAsInt++;
            moveDistanceZRemainder -= 1;
        }

        return new Vertex(xMoveDistanceAsInt, zMoveDistanceAsInt);
    }

    private int moveX(Position current, Position target, int distance) {
        int distanceX = 0;

        if (current.getX() < target.getX()) {
            int difference = target.getX() - current.getX();
            distanceX = distance > difference ? difference : distance;
            collided = !current.incrementX(distanceX);
        } else if (current.getX() > target.getX()) {
            int difference = current.getX() - target.getX();
            distanceX = distance > difference ? difference : distance;
            collided = !current.decrementX(distanceX);
        }

        return distanceX;
    }

    private int moveZ(Position current, Position target, int distance) {
        int distanceZ = 0;

        if (current.getZ() < target.getZ()) {
            int difference = target.getZ() - current.getZ();
            distanceZ = distance > difference ? difference : distance;
            collided = !current.incrementZ(distanceZ);
        } else if (current.getZ() > target.getZ()) {
            int difference = current.getZ() - target.getZ();
            distanceZ = distance > difference ? difference : distance;
            collided = !current.decrementZ(distanceZ);
        }

        return distanceZ;
    }

    private boolean hasFinishedMoving(Position current, Position target) {
        return current.equals(target) || hasCollided();
    }

    @Override
    public void update(LooperContext context) {
        if (isAttacking()) {
            if (isInsideAttackRange(targetAnimate.getPosition())) {
                // we reach our target
                stopMoving();
            } else {
                // we should start moving to getting closer
                move(lastAttackStartTime);
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

    private void clearTargetPosition() {
        targetPosition = null;
    }

    private void clearTargetAnimate() {
        targetAnimate = null;
    }

    public void stopMoving() {
        lastMoveStartTime = null;
    }

    public void stopAttacking() {
        lastAttackStartTime = null;
    }

    protected void dispatch(Packet packet) {
        Game.getInstance().getMap().dispatch(packet);
    }

    protected void dispatch(Packet packet, UUID target) {
        Game.getInstance().getMap().dispatch(packet, target);
    }

    protected void onMove(int distanceX, int distanceZ) {
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
