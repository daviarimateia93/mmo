package com.mmo.core.property;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.mmo.core.map.MapInstance;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PropertyModifier implements MapInstance {

    private final UUID instanceId = UUID.randomUUID();
    private final PropertyModifierAction action;
    private final Integer value;
    private final boolean persisted;
    private final OffsetDateTime expiration;

    @Builder
    public PropertyModifier(
            @NonNull PropertyModifierAction action,
            @NonNull Integer value,
            OffsetDateTime expiration,
            boolean persisted) {

        if (Objects.nonNull(expiration) && !persisted) {
            throw new PropertyModifierIllegalException("Must be persisted when has expiration");
        }

        this.action = action;
        this.value = value;
        this.expiration = expiration;
        this.persisted = persisted;
    }

    public Optional<OffsetDateTime> getExpiration() {
        return Optional.ofNullable(expiration);
    }

    public boolean isExpired() {
        if (Objects.isNull(expiration)) {
            return false;
        }

        return expiration.isBefore(OffsetDateTime.now());
    }
}
