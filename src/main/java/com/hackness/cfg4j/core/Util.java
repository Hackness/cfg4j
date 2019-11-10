package com.hackness.cfg4j.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Hack
 * Date: 11-Nov-19 00:42
 */
public class Util {
    public static Class getGenericType(Field field, int idx) {
        return (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[idx];
    }
}
