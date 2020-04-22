package com.hackness.cfg4j.core.model.file;

import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.parse.IParser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Hack
 * Date: 25-Nov-19 10:18
 */
public interface IFileHandler {
    List<String> getSupportedExtensions();

    default boolean isExtensionSupported(String ext) {
        if (ext.startsWith("."))
            ext = ext.substring(1);
        return getSupportedExtensions().contains(ext.toLowerCase());
    }

    void loadFile(File file);

    IParser getParser();

    TypeManager getTypeManager();

    void init();

    void loadField(Field field, Object owner, File file);
}
