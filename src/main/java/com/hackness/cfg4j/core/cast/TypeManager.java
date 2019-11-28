package com.hackness.cfg4j.core.cast;

import com.hackness.cfg4j.core.Util;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public Object deserialize(Object element, Type castType, String serializedDescriptor) throws Exception {
        Type rawType = Util.getRawType(castType);
        ITypeCaster caster = casters.stream()
                .filter(c -> c.getElementType() == element.getClass())
                .filter(c -> c.getObjectType() == rawType)
                .filter(c -> c.serializedDescriptor().equalsIgnoreCase(serializedDescriptor))
                .findAny()
                .orElseThrow(() -> new NullPointerException(
                        "No appropriate caster found to deserialize " + element + " to " + castType));
        return caster.deserialize(element, castType);
    }

    public Object deserialize(Object element, Type castType) throws Exception {
        Type rawType = Util.getRawType(castType);
        ITypeCaster caster = casters.stream()
                .filter(c -> c.getElementType() == element.getClass())
                .filter(c -> c.getObjectType() == rawType)
                .findAny()
                .orElseThrow(() -> new NullPointerException(
                        "No appropriate caster found to deserialize " + element + " to " + castType));
        return caster.deserialize(element, castType);
    }
}
