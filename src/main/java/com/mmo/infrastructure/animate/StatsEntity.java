package com.mmo.infrastructure.animate;

import com.mmo.core.stat.Stats;

import lombok.Data;

@Data
public class StatsEntity {

    private Integer strength;
    private Integer dexterity;
    private Integer intelligence;
    private Integer concentration;
    private Integer sense;
    private Integer charm;

    public static StatsEntity of(Stats stats) {
        StatsEntity entity = new StatsEntity();
        entity.setStrength(stats.getStrength());
        entity.setDexterity(stats.getDexterity());
        entity.setIntelligence(stats.getIntelligence());
        entity.setConcentration(stats.getConcentration());
        entity.setSense(stats.getSense());
        entity.setCharm(stats.getCharm());

        return entity;
    }

    public Stats toStats() {
        return Stats.builder()
                .strength(getStrength())
                .dexterity(getDexterity())
                .intelligence(getIntelligence())
                .concentration(getConcentration())
                .sense(getSense())
                .charm(getCharm())
                .build();
    }
}
