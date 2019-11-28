package com.hackness.cfg4j.core;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 11-Nov-19 00:42
 */
public class Util {
    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    public static Type getRawType(Type type) {
        if (ParameterizedType.class.isAssignableFrom(type.getClass()))
            return ((ParameterizedType) type).getRawType();
        else
            return type;
    }

    public static Type[] getGenericTypes(Type type) {
        if (!ParameterizedType.class.isAssignableFrom(type.getClass())) {
            throw new IllegalArgumentException("Given type is not a ParameterizedType!");
        }
        return  ((ParameterizedType) type).getActualTypeArguments();
    }
}
