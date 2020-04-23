package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class RawDoubleCaster<E> implements ITypeCaster<E, Double> {
    @Override
    public String serializedDescriptor() {
        return "double";
    }

    @Override
    public Class<Double> getObjectType() {
        return Double.class;
    }

    @Override
    public Double emptyObjectInstance() {
        return 0D;
    }
}
