package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class RawByteCaster<E> implements ITypeCaster<E, Byte> {
    @Override
    public String serializedDescriptor() {
        return "byte";
    }

    @Override
    public Class<Byte> getObjectType() {
        return byte.class;
    }

    @Override
    public Byte emptyObjectInstance() {
        return 0;
    }
}
