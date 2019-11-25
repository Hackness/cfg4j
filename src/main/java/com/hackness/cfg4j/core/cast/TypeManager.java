package com.hackness.cfg4j.core.cast;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:58
 */
public class TypeManager {
    @Getter(lazy = true) private static final TypeManager instance = new TypeManager();
    private List<ITypeCaster> casters = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <E, O> ITypeCaster<E, O> getTypeCaster(Class<E> elementType, Class<O> objectType) {
        return casters.stream()
                .filter(c -> c.getElementType() == elementType)
                .filter(c -> c.getObjectType() == objectType)
                .findAny()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <E> ITypeCaster<E, Object> getTypeCaster(Class<E> elementType, String serializedName) {
        return casters.stream()
                .filter(c -> c.getElementType() == elementType)
                .filter(c -> c.serializedName().equalsIgnoreCase(serializedName))
                .findAny()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <E, O> O deserialize(E element, Class<O> castClass) {
        ITypeCaster<E, O> caster = getTypeCaster((Class<E>) element.getClass(), castClass);
        if (caster == null)
            throw new NullPointerException("No appropriate caster found to deserialize " + element + " to " + castClass);
        return caster.deserialize(element);
    }

    public <E> Object deserialize(E element, String typeName) {
        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

}
