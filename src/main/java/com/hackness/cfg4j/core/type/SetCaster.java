package com.hackness.cfg4j.core.type;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:53
 */
public abstract class SetCaster<E> extends InstancedCaster<E, Set> {
    @Override
    public String serializedDescriptor() {
        return "set";
    }

    @Override
    public Class<Set> getObjectType() {
        return Set.class;
    }

    @Override
    public Set emptyObjectInstance() {
        return new HashSet();
    }
}
