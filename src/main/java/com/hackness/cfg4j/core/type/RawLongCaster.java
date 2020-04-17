package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class RawLongCaster<E> implements ITypeCaster<E, Long> {
    @Override
    public String serializedDescriptor() {
        return "long";
    }

    @Override
    public Class<Long> getObjectType() {
        return long.class;
    }

    @Override
    public Long emptyObjectInstance() {
        return 0L;
    }
}
