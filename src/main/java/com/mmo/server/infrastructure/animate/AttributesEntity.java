package com.mmo.server.infrastructure.animate;

import com.mmo.server.core.attribute.Attributes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class AttributesEntity {

    @Setter(AccessLevel.NONE)
    private Integer hp;
    @Setter(AccessLevel.NONE)
    private Integer mp;
    private Integer attack;
    private Integer defense;
    private Integer magicDefense;
    private Integer hitRate;
    private Integer critical;
    private Integer dodgeRate;
    private Integer attackSpeed;
    private Integer moveSpeed;
    @Setter(AccessLevel.NONE)
    private Integer hpRecovery;
    @Setter(AccessLevel.NONE)
    private Integer mpRecovery;
    private Integer attackRange;

    public void setHP(Integer hp) {
        this.hp = hp;
    }

    public Integer getHP() {
        return hp;
    }

    public void setMP(Integer mp) {
        this.mp = mp;
    }

    public Integer getMP() {
        return mp;
    }

    public void setHPRecovery(Integer hpRecovery) {
        this.hpRecovery = hpRecovery;
    }

    public Integer getHPRecovery() {
        return hpRecovery;
    }

    public void setMPRecovery(Integer mpRecovery) {
        this.mpRecovery = mpRecovery;
    }

    public Integer getMPRecovery() {
        return mpRecovery;
    }

    public static AttributesEntity of(Attributes stats) {
        AttributesEntity entity = new AttributesEntity();
        entity.setHP(stats.getHP());
        entity.setMP(stats.getMP());
        entity.setAttack(stats.getAttack());
        entity.setDefense(stats.getDefense());
        entity.setMagicDefense(stats.getMagicDefense());
        entity.setHitRate(stats.getHitRate());
        entity.setCritical(stats.getCritical());
        entity.setDodgeRate(stats.getDodgeRate());
        entity.setAttackSpeed(stats.getAttackSpeed());
        entity.setMoveSpeed(stats.getMoveSpeed());
        entity.setHPRecovery(stats.getHPRecovery());
        entity.setMPRecovery(stats.getMPRecovery());
        entity.setAttackRange(stats.getAttackRange());

        return entity;
    }

    public Attributes toAttributes() {
        return Attributes.builder()
                .hp(getHP())
                .mp(getMP())
                .attack(getAttack())
                .defense(getDefense())
                .magicDefense(getMagicDefense())
                .hitRate(getHitRate())
                .critical(getCritical())
                .dodgeRate(getDodgeRate())
                .attackSpeed(getAttackSpeed())
                .moveSpeed(getMoveSpeed())
                .hpRecovery(getHPRecovery())
                .mpRecovery(getMPRecovery())
                .attackRange(
                        getAttackRange())
                .build();
    }
}