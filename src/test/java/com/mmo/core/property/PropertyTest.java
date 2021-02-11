package com.mmo.core.property;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.mmo.core.looper.LooperContextMocker;

public class PropertyTest {

    @Test
    public void modify() throws InterruptedException {
        Property str = Property.builder()
                .name("str")
                .value(34)
                .build();

        Property defense = Property.builder()
                .name("defense")
                .value(257)
                .build();

        PropertyModifier incrementStr15 = PropertyModifier.builder()
                .action(PropertyModifierAction.INCREMENT)
                .value(15)
                .persisted(true)
                .build();

        PropertyModifier decrementStr5 = PropertyModifier.builder()
                .action(PropertyModifierAction.DECREMENT)
                .value(5)
                .expiration(OffsetDateTime.now().plusSeconds(2))
                .persisted(true)
                .build();

        PropertyModifier decrementStr = PropertyModifier.builder()
                .action(PropertyModifierAction.DECREMENT)
                .value(1)
                .build();

        str.modify(incrementStr15);
        str.modify(decrementStr5);
        str.modify(decrementStr);

        assertThat(str.getValue(), equalTo(34));
        assertThat(str.getFinalValue(), equalTo(43));
        assertThat(str.getModifiers(), containsInAnyOrder(incrementStr15, decrementStr5));
        assertThat(str.getModifiers().size(), equalTo(2));
        assertThat(defense.getValue(), equalTo(257));
        assertThat(defense.getFinalValue(), equalTo(257));

        LooperContextMocker.update(str, 5000);

        assertThat(str.getFinalValue(), equalTo(48));
    }
}
