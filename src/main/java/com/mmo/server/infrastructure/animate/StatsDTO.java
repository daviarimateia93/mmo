package com.mmo.server.infrastructure.animate;

import com.mmo.server.core.stat.Stats;

import lombok.Data;

@Data
public class StatsDTO {

    private Integer strength;
    private Integer dexterity;
    private Integer intelligence;
    private Integer concentration;
    private Integer sense;
    private Integer charm;

    public static StatsDTO of(Stats stats) {
        StatsDTO dto = new StatsDTO();
        dto.setStrength(stats.getStrength());
        dto.setDexterity(stats.getDexterity());
        dto.setIntelligence(stats.getIntelligence());
        dto.setConcentration(stats.getConcentration());
        dto.setSense(stats.getSense());
        dto.setCharm(stats.getCharm());

        return dto;
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
