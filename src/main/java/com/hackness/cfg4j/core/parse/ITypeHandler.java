package com.hackness.cfg4j.core.parse;

/**
 * Created by Hack
 * Date: 10-Nov-19 21:23
 *
 * Model of each type that will be parsed. Responsible for serialization and deserialization of a given type.
 * Can be identified by defines in config file, for example: option_map should be parsed by MapHandler
 */
public interface ITypeHandler<T> {
    String getNodeName();

    void deserialize(Class<?> owner, String fieldName, T node);
}
