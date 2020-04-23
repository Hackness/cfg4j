package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class RawIntegerCaster<E> implements ITypeCaster<E, Integer> {
    @Override
    public String serializedDescriptor() {
        return "int";
    }

    @Override
    public Class<Integer> getObjectType() {
        return int.class;
    }

    @Override
    public Integer emptyObjectInstance() {
        return 0;
    }
}
