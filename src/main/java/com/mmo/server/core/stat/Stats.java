package com.mmo.server.core.stat;

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
public class Stats implements LooperUpdater {

    private final Properties properties;

    @Builder
    private Stats(
            @NonNull Integer strength,
            @NonNull Integer dexterity,
            @NonNull Integer intelligence,
            @NonNull Integer concentration,
            @NonNull Integer sense,
            @NonNull Integer charm) {

        properties = Properties.builder()
                .add(newProperty(Stat.STRENGTH, strength))
                .add(newProperty(Stat.DEXTERITY, dexterity))
                .add(newProperty(Stat.INTELLIGENCE, intelligence))
                .add(newProperty(Stat.CONCENTRATION, concentration))
                .add(newProperty(Stat.SENSE, sense))
                .add(newProperty(Stat.CHARM, charm))
                .build();
    }

    public Integer getStrength() {
        return getValue(Stat.STRENGTH);
    }

    public Integer getFinalStrength() {
        return getFinalValue(Stat.STRENGTH);
    }

    public Integer getDexterity() {
        return getValue(Stat.DEXTERITY);
    }

    public Integer getFinalDexterity() {
        return getFinalValue(Stat.DEXTERITY);
    }

    public Integer getIntelligence() {
        return getValue(Stat.INTELLIGENCE);
    }

    public Integer getFinalIntelligence() {
        return getFinalValue(Stat.INTELLIGENCE);
    }

    public Integer getConcentration() {
        return getValue(Stat.CONCENTRATION);
    }

    public Integer getFinalConcentration() {
        return getFinalValue(Stat.CONCENTRATION);
    }

    public Integer getSense() {
        return getValue(Stat.SENSE);
    }

    public Integer getFinalSense() {
        return getFinalValue(Stat.SENSE);
    }

    public Integer getCharm() {
        return getValue(Stat.CHARM);
    }

    public Integer getFinalCharm() {
        return getFinalValue(Stat.CHARM);
    }

    public void modify(StatModifier modifier) {
        properties.modify(modifier.getStat().name(), modifier.toPropertyModifier());
    }

    private Integer getValue(Stat stat) {
        return properties.getValue(stat.name());
    }

    private Integer getFinalValue(Stat stat) {
        return properties.getFinalValue(stat.name());
    }

    private Property newProperty(Stat stat, Integer value) {
        return Property.builder()
                .name(stat.name())
                .value(value)
                .build();
    }

    @Override
    public void update(LooperContext context) {
        properties.forEach(property -> property.update(context));
    }
}
