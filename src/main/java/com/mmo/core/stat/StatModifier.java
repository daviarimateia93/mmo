package com.mmo.core.stat;

import java.time.OffsetDateTime;

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
    private OffsetDateTime expiration;
    private boolean persisted;

    @Builder
    private StatModifier(
            PropertyModifierAction action,
            Stat stat,
            Integer value,
            OffsetDateTime expiration,
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
