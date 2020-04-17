package com.hackness.cfg4j.core.cast;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 25-Nov-19 13:00
 */
public interface ITypeCaster<E, O> {
    /**
     * @return - the name used to identify a stored type in a serialized form.
     * For example:
     * <list name="someValue">
     *     ...
     * </list>
     * In such a form the "list" in the beginning of the definition marks that it's a List element.
     */
    String serializedDescriptor();

    /**
     * CURRENTLY NOT IMPLEMENTED
     */
    E serialize(O obj, Type type, Field field);

    /**
     *
     * @param element - element instance that store a value in a serialized form
     * @param type - the generic type of a field to which the element will be casted.
     *             Warning: do not try to give generic types of dynamic instances, it will be unable to obtain
     *             their actual generic types due to java restrictions. The real generic types can only be found
     *             from fields this way: field.getGenericType(). Generic data from variables created in a method
     *             will not give the real used generic types what brings inability to cast their values.
     * @return - new instance of the type with converted data from the serialized form. If no default constructor for
     *         an object will be found the emptyInstance() method will be used to create the instance.
     */
    O deserialize(E element, Type type, Field field) throws Exception;

    /**
     * @return - class of the serialized object
     */
    Class<E> getElementType();

    /**
     * @return - class of the deserialized object
     */
    Class<O> getObjectType();

    /**
     * @return - new empty instance of the type. Will be used in deserialization in case no default constructor was
     *         found accessible
     */
    O emptyObjectInstance();
}
