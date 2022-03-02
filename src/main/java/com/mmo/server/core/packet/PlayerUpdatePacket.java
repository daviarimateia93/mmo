package com.mmo.server.core.packet;

import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.animate.Animate;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.player.Player;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PlayerUpdatePacket implements NetworkPacket {

    public static final String ALIAS = "PLAYER_UPDATE";

    private final UUID source;
    private final String name;
    private final Integer positionX;
    private final Integer positionZ;
    private final Integer statsStrength;
    private final Integer statsDexterity;
    private final Integer statsIntelligence;
    private final Integer statsConcentration;
    private final Integer statsSense;
    private final Integer statsCharm;
    private final Integer attributesHP;
    private final Integer attributesMP;
    private final Integer attributesAttack;
    private final Integer attributesDefense;
    private final Integer attributesMagicDefense;
    private final Integer attributesHitRate;
    private final Integer attributesCritical;
    private final Integer attributesDodgeRate;
    private final Integer attributesAttackSpeed;
    private final Integer attributesMoveSpeed;
    private final Integer attributesHPRecovery;
    private final Integer attributesMPRecovery;
    private final Integer attributesAttackRange;
    private final boolean alive;
    private final boolean moving;
    private final Integer targetPositionX;
    private final Integer targetPositionZ;
    private final boolean attacking;
    private final UUID targetAnimate;

    @Builder
    private PlayerUpdatePacket(@NonNull UUID source, @NonNull Player player) {
        this(
                source,
                player.getName(),
                player.getPosition().getX(),
                player.getPosition().getZ(),
                player.getStats().getStrength(),
                player.getStats().getDexterity(),
                player.getStats().getIntelligence(),
                player.getStats().getConcentration(),
                player.getStats().getSense(),
                player.getStats().getCharm(),
                player.getAttributes().getHP(),
                player.getAttributes().getMP(),
                player.getAttributes().getAttack(),
                player.getAttributes().getDefense(),
                player.getAttributes().getMagicDefense(),
                player.getAttributes().getHitRate(),
                player.getAttributes().getCritical(),
                player.getAttributes().getDodgeRate(),
                player.getAttributes().getAttackSpeed(),
                player.getAttributes().getMoveSpeed(),
                player.getAttributes().getHPRecovery(),
                player.getAttributes().getMPRecovery(),
                player.getAttributes().getAttackRange(),
                player.isAlive(),
                player.isMoving(),
                player.getTargetPosition().map(Position::getX).orElse(null),
                player.getTargetPosition().map(Position::getZ).orElse(null),
                player.isAttacking(),
                player.getTargetAnimate().map(Animate::getInstanceId).orElse(null));
    }

    @Builder(builderMethodName = "dtoBuilder", buildMethodName = "buildDTO")
    private PlayerUpdatePacket(
            @NonNull UUID source,
            @NonNull String name,
            @NonNull Integer positionX,
            @NonNull Integer positionZ,
            @NonNull Integer statsStrength,
            @NonNull Integer statsDexterity,
            @NonNull Integer statsIntelligence,
            @NonNull Integer statsConcentration,
            @NonNull Integer statsSense,
            @NonNull Integer statsCharm,
            @NonNull Integer attributesHP,
            @NonNull Integer attributesMP,
            @NonNull Integer attributesAttack,
            @NonNull Integer attributesDefense,
            @NonNull Integer attributesMagicDefense,
            @NonNull Integer attributesHitRate,
            @NonNull Integer attributesCritical,
            @NonNull Integer attributesDodgeRate,
            @NonNull Integer attributesAttackSpeed,
            @NonNull Integer attributesMoveSpeed,
            @NonNull Integer attributesHPRecovery,
            @NonNull Integer attributesMPRecovery,
            @NonNull Integer attributesAttackRange,
            boolean alive,
            boolean moving,
            Integer targetPositionX,
            Integer targetPositionZ,
            boolean attacking,
            UUID targetAnimate) {

        this.source = source;
        this.name = name;
        this.positionX = positionX;
        this.positionZ = positionZ;
        this.statsStrength = statsStrength;
        this.statsDexterity = statsDexterity;
        this.statsIntelligence = statsIntelligence;
        this.statsConcentration = statsConcentration;
        this.statsSense = statsSense;
        this.statsCharm = statsCharm;
        this.attributesHP = attributesHP;
        this.attributesMP = attributesMP;
        this.attributesAttack = attributesAttack;
        this.attributesDefense = attributesDefense;
        this.attributesMagicDefense = attributesMagicDefense;
        this.attributesHitRate = attributesHitRate;
        this.attributesCritical = attributesCritical;
        this.attributesDodgeRate = attributesDodgeRate;
        this.attributesAttackSpeed = attributesAttackSpeed;
        this.attributesMoveSpeed = attributesMoveSpeed;
        this.attributesHPRecovery = attributesHPRecovery;
        this.attributesMPRecovery = attributesMPRecovery;
        this.attributesAttackRange = attributesAttackRange;
        this.alive = alive;
        this.moving = moving;
        this.targetPositionX = targetPositionX;
        this.targetPositionZ = targetPositionZ;
        this.attacking = attacking;
        this.targetAnimate = targetAnimate;
    }

    @Override
    public String getAlias() {
        return ALIAS;
    }

    public Optional<Integer> getTargetPositionX() {
        return Optional.ofNullable(targetPositionX);
    }

    public Optional<Integer> getTargetPositionZ() {
        return Optional.ofNullable(targetPositionZ);
    }

    public Optional<UUID> getTargetAnimate() {
        return Optional.ofNullable(targetAnimate);
    }
}
