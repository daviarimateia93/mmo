package com.mmo.server.core.stat;

import java.time.OffsetDateTime;

import com.mmo.server.core.property.PropertyModifier;
import com.mmo.server.core.property.PropertyModifierAction;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class StatModifier {

    private final PropertyModifierAction action;
    private final Stat stat;
    private final Integer value;
    private final OffsetDateTime expiration;
    private final boolean persisted;

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
