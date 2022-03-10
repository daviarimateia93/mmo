package com.mmo.server.infrastructure.animate;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.mmo.server.core.attribute.Attributes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AttributesDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer hp;
    @Getter(AccessLevel.NONE)
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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer hpRecovery;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer mpRecovery;
    private Integer attackRange;

    @BsonProperty("hp")
    public void setHP(Integer hp) {
        this.hp = hp;
    }

    @BsonProperty("hp")
    public Integer getHP() {
        return hp;
    }

    @BsonProperty("mp")
    public void setMP(Integer mp) {
        this.mp = mp;
    }

    @BsonProperty("mp")
    public Integer getMP() {
        return mp;
    }

    @BsonProperty("hpRecovery")
    public void setHPRecovery(Integer hpRecovery) {
        this.hpRecovery = hpRecovery;
    }

    @BsonProperty("hpRecovery")
    public Integer getHPRecovery() {
        return hpRecovery;
    }

    @BsonProperty("mpRecovery")
    public void setMPRecovery(Integer mpRecovery) {
        this.mpRecovery = mpRecovery;
    }

    @BsonProperty("mpRecovery")
    public Integer getMPRecovery() {
        return mpRecovery;
    }

    public static AttributesDTO of(Attributes attributes) {
        AttributesDTO dto = new AttributesDTO();
        dto.setHP(attributes.getHP());
        dto.setMP(attributes.getMP());
        dto.setAttack(attributes.getAttack());
        dto.setDefense(attributes.getDefense());
        dto.setMagicDefense(attributes.getMagicDefense());
        dto.setHitRate(attributes.getHitRate());
        dto.setCritical(attributes.getCritical());
        dto.setDodgeRate(attributes.getDodgeRate());
        dto.setAttackSpeed(attributes.getAttackSpeed());
        dto.setMoveSpeed(attributes.getMoveSpeed());
        dto.setHPRecovery(attributes.getHPRecovery());
        dto.setMPRecovery(attributes.getMPRecovery());
        dto.setAttackRange(attributes.getAttackRange());

        return dto;
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
                .attackRange(getAttackRange())
                .build();
    }
}