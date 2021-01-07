package com.mmo.core.property;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PropertiesTest {

    private static Property str;
    private static Property dex;
    private static Properties properties;

    @BeforeAll
    public static void setup() {
        str = Property.builder()
                .name("str")
                .value(20)
                .build();

        str.modify(PropertyModifier.builder()
                .action(PropertyModifierAction.INCREMENT)
                .value(15)
                .build());

        dex = Property.builder()
                .name("dex")
                .value(89)
                .build();

        properties = Properties.builder()
                .add(str)
                .add(dex)
                .build();
    }

    @Test
    public void getStr() {
        Optional<Property> expected = Optional.of(str);
        Optional<Property> result = properties.get(str.getName());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getDex() {
        Optional<Property> expected = Optional.of(dex);
        Optional<Property> result = properties.get(dex.getName());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getNotSetted() {
        Optional<Property> expected = Optional.empty();
        Optional<Property> result = properties.get("notSetted");

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getRequired() {
        Property expected = str;
        Property result = properties.getRequired(str.getName());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getRequiredThrowsExceptionWhenNotFound() {
        assertThrows(PropertyNotFoundException.class, () -> properties.getRequired("notSetted"));
    }

    @Test
    public void getValue() {
        Integer expected = str.getValue();
        Integer result = properties.getValue(str.getName());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getValueThrowsExceptionWhenNotFound() {
        assertThrows(PropertyNotFoundException.class, () -> properties.getValue("notSetted"));
    }

    @Test
    public void getFinalValue() {
        Integer expected = str.getFinalValue();
        Integer result = properties.getFinalValue(str.getName());

        assertThat(result, equalTo(expected));
    }

    @Test
    public void getFinalValueThrowsExceptionWhenNotFound() {
        assertThrows(PropertyNotFoundException.class, () -> properties.getFinalValue("notSetted"));
    }

    @Test
    public void iterator() {
        assertThat(properties.iterator(), notNullValue());

        Property[] expected = { str, dex };
        List<Property> result = new ArrayList<>();

        for (Property property : properties) {
            result.add(property);
        }

        assertThat(result, containsInAnyOrder(expected));
    }
}
