package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class RawShortCaster<E> implements ITypeCaster<E, Short> {
    @Override
    public String serializedDescriptor() {
        return "short";
    }

    @Override
    public Class<Short> getObjectType() {
        return short.class;
    }

    @Override
    public Short emptyObjectInstance() {
        return 0;
    }
}
