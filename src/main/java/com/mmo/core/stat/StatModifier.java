package com.mmo.core.stat;

import java.time.ZonedDateTime;

import com.mmo.core.property.PropertyModifier;
import com.mmo.core.property.PropertyModifierAction;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class StatModifier {

    private PropertyModifierAction action;
    private Stat stat;
    private Integer value;
    private ZonedDateTime expiration;
    private boolean persisted;

    @Builder
    private StatModifier(
            PropertyModifierAction action,
            Stat stat,
            Integer value,
            ZonedDateTime expiration,
            boolean persisted) {

        this.action = action;
        this.stat = stat;
        this.value = value;
        this.expiration = expiration;
        this.persisted = persisted;
    }

    public PropertyModifier toPropertyModifier() {
        return PropertyModifier.builder()
                .action(action)
                .value(value)
                .expiration(expiration)
                .persisted(persisted)
                .build();
    }
}
