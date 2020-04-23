package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:55
 */
public abstract class StringCaster<E> implements ITypeCaster<E, String> {
    @Override
    public String serializedDescriptor() {
        return "string";
    }

    @Override
    public Class<String> getObjectType() {
        return String.class;
    }

    @Override
    public String emptyObjectInstance() {
        return "";
    }
}
