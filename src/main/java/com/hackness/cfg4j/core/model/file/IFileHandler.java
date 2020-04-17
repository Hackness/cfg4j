package com.hackness.cfg4j.core.model.file;

import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.parse.IParser;

import java.io.File;
import java.util.List;

/**
 * Created by Hack
 * Date: 25-Nov-19 10:18
 */
public interface IFileHandler {
    List<String> getSupportedExtensions();

    default boolean isExtensionSupported(String ext) {
        return getSupportedExtensions().contains(ext.toLowerCase());
    }

    void loadFile(File file, Object owner);

    IParser getParser();

    TypeManager getTypeManager();

    void init();
}
