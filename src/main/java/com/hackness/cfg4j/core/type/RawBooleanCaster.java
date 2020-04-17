package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class RawBooleanCaster<E> implements ITypeCaster<E, Boolean> {
    @Override
    public String serializedDescriptor() {
        return "bool";
    }

    @Override
    public Class<Boolean> getObjectType() {
        return boolean.class;
    }

    @Override
    public Boolean emptyObjectInstance() {
        return false;
    }
}
