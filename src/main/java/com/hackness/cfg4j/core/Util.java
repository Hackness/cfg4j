package com.hackness.cfg4j.core;

import java.io.File;
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

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
}
