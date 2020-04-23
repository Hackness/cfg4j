package com.hackness.cfg4j.core.type;

import com.hackness.cfg4j.core.Util;
import com.hackness.cfg4j.core.cast.ITypeCaster;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 11:27
 */
public abstract class InstancedCaster<E, O> implements ITypeCaster<E, O> {
    protected O newInstance(Type type) throws Exception {
        Class rawType = (Class) Util.getRawType(type);
        Constructor constructor = null;
        try {
            constructor = rawType.getConstructor();
        } catch (Exception e) {
            // no default accessible constructor found
        }
        O out;
        if (constructor != null)
            out = (O) constructor.newInstance();
        else
            out = emptyObjectInstance();
        return out;
    }
}
