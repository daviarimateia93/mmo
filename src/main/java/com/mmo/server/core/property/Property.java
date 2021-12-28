package com.mmo.server.core.property;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mmo.server.core.looper.LooperContext;
import com.mmo.server.core.looper.LooperUpdater;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Property implements LooperUpdater {

    private final Set<PropertyModifier> modifiers = new LinkedHashSet<>();
    private final String name;
    private final Integer value;
    private Integer finalValue;

    @Builder
    public Property(@NonNull String name, @NonNull Integer value) {
        this.name = name;
        this.value = value;
        this.finalValue = value;
    }

    public Set<PropertyModifier> getModifiers() {
        return Collections.unmodifiableSet(modifiers);
    }

    public Integer getFinalValue() {
        return finalValue;
    }

    public void modify(PropertyModifier modifier) throws PropertyModifierIllegalException {
        if (modifier.isPersisted()) {
            modifiers.add(modifier);
        }

        applyModifier(modifier);
    }

    private void applyModifier(PropertyModifier modifier) {
        applyModifier(modifier.getAction(), modifier.getValue());
    }

    private void applyModifier(PropertyModifierAction action, Integer value) {
        switch (action) {
        case INCREMENT:
            increment(value);
            break;

        case DECREMENT:
            decrement(value);
            break;

        default:
            throw new PropertyModifierIllegalException("Invalid modifier, action %s does not exist", action);
        }
    }

    private void releaveModifier(PropertyModifier modifier) {
        PropertyModifierAction reversedAction = reverse(modifier.getAction());
        applyModifier(reversedAction, modifier.getValue());
        modifiers.remove(modifier);
    }

    private PropertyModifierAction reverse(PropertyModifierAction action) {
        switch (action) {
        case INCREMENT:
            return PropertyModifierAction.DECREMENT;

        case DECREMENT:
            return PropertyModifierAction.INCREMENT;

        default:
            throw new PropertyModifierIllegalException("Invalid modifier, action %s does not exist", action);
        }
    }

    private void increment(Integer value) {
        finalValue += value;
    }

    private void decrement(Integer value) {
        finalValue -= value;
    }

    private void releaveExpiredModifiers() {
        modifiers
                .stream()
                .filter(modifier -> modifier.getExpiration().isPresent())
                .filter(PropertyModifier::isExpired)
                .forEach(this::releaveModifier);
    }

    @Override
    public void update(LooperContext context) throws PropertyModifierIllegalException {
        releaveExpiredModifiers();
    }
}
