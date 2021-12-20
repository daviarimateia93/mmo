package com.mmo.core.property;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Properties implements Iterable<Property> {

    private final Map<String, Property> values = new LinkedHashMap<>();

    private Properties() {

    }

    @Override
    public Iterator<Property> iterator() {
        return values.values().iterator();
    }

    public Optional<Property> get(String name) {
        return Optional.ofNullable(values.get(name));
    }

    public Property getRequired(String name) throws PropertyNotFoundException {
        return get(name).orElseThrow(() -> new PropertyNotFoundException(name));
    }

    public Integer getValue(String name) {
        return getRequired(name).getValue();
    }

    public Integer getFinalValue(String name) {
        return getRequired(name).getFinalValue();
    }

    public void modify(String propertyName, PropertyModifier modifier) {
        getRequired(propertyName).modify(modifier);
    }

    private void put(Property property) {
        values.put(property.getName(), property);
    }

    public static PropertiesBuilder builder() {
        return new PropertiesBuilder();
    }

    public static class PropertiesBuilder {

        private Set<Property> properties = new LinkedHashSet<>();

        public PropertiesBuilder add(Property property) {
            this.properties.add(property);
            return this;
        }

        public Properties build() {
            Properties properties = new Properties();

            this.properties.forEach(properties::put);

            return properties;
        }
    }
}
