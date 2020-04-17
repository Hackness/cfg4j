package com.hackness.cfg4j.core.type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:32
 */
public abstract class ListCaster<E> extends InstancedCaster<E, List> {
    @Override
    public String serializedDescriptor() {
        return "list";
    }

    @Override
    public Class<List> getObjectType() {
        return List.class;
    }

    @Override
    public List emptyObjectInstance() {
        return new ArrayList();
    }
}
