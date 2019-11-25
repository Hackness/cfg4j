package com.hackness.cfg4j.core.cast;

/**
 * Created by Hack
 * Date: 25-Nov-19 13:00
 */
public interface ITypeCaster<E, O> {
    String serializedName();

    E serialize(O obj);

    O deserialize(E element);

    Class<E> getElementType();

    Class<O> getObjectType();
}
