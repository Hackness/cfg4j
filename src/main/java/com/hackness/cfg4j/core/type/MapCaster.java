package com.hackness.cfg4j.core.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:49
 */
public abstract class MapCaster<E> extends InstancedCaster<E, Map> {
    @Override
    public String serializedDescriptor() {
        return "map";
    }

    @Override
    public Class<Map> getObjectType() {
        return Map.class;
    }

    @Override
    public Map emptyObjectInstance() {
        return new HashMap();
    }
}
