package com.mmo.server.core.attribute;

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
public class AttributeModifier {

    private final PropertyModifierAction action;
    private final Attribute attribute;
    private final Integer value;
    private final OffsetDateTime expiration;
    private final boolean persisted;

    @Builder
    private AttributeModifier(
            PropertyModifierAction action,
            Attribute attribute,
            Integer value,
            OffsetDateTime expiration,
            boolean persisted) {

        this.action = action;
        this.attribute = attribute;
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
