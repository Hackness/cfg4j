package com.hackness.cfg4j.core.cast;

import com.hackness.cfg4j.core.Util;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:58
 */
public class TypeManager {
    @Getter(lazy = true) private static final TypeManager instance = new TypeManager();
    private List<ITypeCaster> casters = new ArrayList<>();

    public void registerTypeCaster(ITypeCaster caster) {
        casters.add(caster);
    }

    public ITypeCaster find(Class elementClass, Type objType, String serializedDescriptor) {
        Predicate<ITypeCaster> cond = c -> true;
        if (elementClass != null)
            cond = cond.and(c -> c.getElementType() == elementClass);
        if (objType != null)
            cond = cond.and(c -> c.getObjectType() == Util.getRawType(objType));
        if (serializedDescriptor != null && !serializedDescriptor.isEmpty())
            cond = cond.and(c -> c.serializedDescriptor().equalsIgnoreCase(serializedDescriptor));
        return casters.stream()
                .filter(cond)
                .findAny()
                .orElseThrow(() -> new NullPointerException(
                        "No appropriate caster found. Element type: " + elementClass + ", Object type: " + objType
                                + ", Descriptor: " + serializedDescriptor));
    }

    public Object deserialize(Object element, Type castType, String serializedDescriptor) throws Exception {
        ITypeCaster caster = find(element.getClass(), castType, serializedDescriptor);
        return caster.deserialize(element, castType);
    }

    public Object deserialize(Object element, Type castType) throws Exception {
        ITypeCaster caster = find(element.getClass(), castType, null);
        return caster.deserialize(element, castType);
    }

    public <E> E serialize(Object obj, Type objType, Class<E> elementClass) {
        ITypeCaster caster = find(elementClass, objType, null);
        return (E) caster.serialize(obj, objType);
    }
}
