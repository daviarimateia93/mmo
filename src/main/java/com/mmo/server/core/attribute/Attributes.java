package com.mmo.server.core.attribute;

import com.mmo.server.core.looper.LooperContext;
import com.mmo.server.core.looper.LooperUpdater;
import com.mmo.server.core.property.Properties;
import com.mmo.server.core.property.Property;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Attributes implements LooperUpdater {

    private final Properties properties;

    @Builder
    private Attributes(
            @NonNull Integer hp,
            @NonNull Integer mp,
            @NonNull Integer attack,
            @NonNull Integer defense,
            @NonNull Integer magicDefense,
            @NonNull Integer hitRate,
            @NonNull Integer critical,
            @NonNull Integer dodgeRate,
            @NonNull Integer attackSpeed,
            @NonNull Integer moveSpeed,
            @NonNull Integer hpRecovery,
            @NonNull Integer mpRecovery,
            @NonNull Integer attackRange) {

        properties = Properties.builder()
                .add(newProperty(Attribute.HP, hp))
                .add(newProperty(Attribute.MP, mp))
                .add(newProperty(Attribute.ATTACK, attack))
                .add(newProperty(Attribute.DEFENSE, defense))
                .add(newProperty(Attribute.MAGIC_DEFENSE, magicDefense))
                .add(newProperty(Attribute.HIT_RATE, hitRate))
                .add(newProperty(Attribute.CRITICAL, critical))
                .add(newProperty(Attribute.DODGE_RATE, dodgeRate))
                .add(newProperty(Attribute.ATTACK_SPEED, attackSpeed))
                .add(newProperty(Attribute.MOVE_SPEED, moveSpeed))
                .add(newProperty(Attribute.HP_RECOVERY, hpRecovery))
                .add(newProperty(Attribute.MP_RECOVERY, mpRecovery))
                .add(newProperty(Attribute.ATTACK_RANGE, attackRange))
                .build();
    }

    public Integer getHP() {
        return getValue(Attribute.HP);
    }

    public Integer getFinalHP() {
        return getFinalValue(Attribute.HP);
    }

    public Integer getMP() {
        return getValue(Attribute.MP);
    }

    public Integer getFinalMP() {
        return getFinalValue(Attribute.MP);
    }

    public Integer getAttack() {
        return getValue(Attribute.ATTACK);
    }

    public Integer getFinalAttack() {
        return getFinalValue(Attribute.ATTACK);
    }

    public Integer getDefense() {
        return getValue(Attribute.DEFENSE);
    }

    public Integer getFinalDefense() {
        return getFinalValue(Attribute.DEFENSE);
    }

    public Integer getMagicDefense() {
        return getValue(Attribute.MAGIC_DEFENSE);
    }

    public Integer getFinalMagicDefense() {
        return getFinalValue(Attribute.MAGIC_DEFENSE);
    }

    public Integer getHitRate() {
        return getValue(Attribute.HIT_RATE);
    }

    public Integer getFinalHitRate() {
        return getFinalValue(Attribute.HIT_RATE);
    }

    public Integer getCritical() {
        return getValue(Attribute.CRITICAL);
    }

    public Integer getFinalCritical() {
        return getFinalValue(Attribute.CRITICAL);
    }

    public Integer getDodgeRate() {
        return getValue(Attribute.DODGE_RATE);
    }

    public Integer getFinalDodgeRate() {
        return getFinalValue(Attribute.DODGE_RATE);
    }

    public Integer getAttackSpeed() {
        return getValue(Attribute.ATTACK_SPEED);
    }

    public Integer getFinalAttackSpeed() {
        return getFinalValue(Attribute.ATTACK_SPEED);
    }

    public Integer getMoveSpeed() {
        return getValue(Attribute.MOVE_SPEED);
    }

    public Integer getFinalMoveSpeed() {
        return getFinalValue(Attribute.MOVE_SPEED);
    }

    public Integer getHPRecovery() {
        return getValue(Attribute.HP_RECOVERY);
    }

    public Integer getFinalHPRecovery() {
        return getFinalValue(Attribute.HP_RECOVERY);
    }

    public Integer getMPRecovery() {
        return getValue(Attribute.MP_RECOVERY);
    }

    public Integer getFinalMPRecovery() {
        return getFinalValue(Attribute.MP_RECOVERY);
    }

    public Integer getAttackRange() {
        return getValue(Attribute.ATTACK_RANGE);
    }

    public Integer getFinalAttackRange() {
        return getFinalValue(Attribute.ATTACK_RANGE);
    }

    public void modify(AttributeModifier modifier) {
        properties.modify(modifier.getAttribute().name(), modifier.toPropertyModifier());
    }

    private Integer getValue(Attribute Attribute) {
        return properties.getValue(Attribute.name());
    }

    private Integer getFinalValue(Attribute Attribute) {
        return properties.getFinalValue(Attribute.name());
    }

    private Property newProperty(Attribute Attribute, Integer value) {
        return Property.builder()
                .name(Attribute.name())
                .value(value)
                .build();
    }

    @Override
    public void update(LooperContext context) {
        properties.forEach(property -> property.update(context));
    }
}
