package com.hackness.cfg4j.core.model.file;

import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.model.GenData;
import com.hackness.cfg4j.core.parse.IParser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by Hack
 * Date: 25-Nov-19 10:18
 */
public interface IFileHandler<E> {
    /**
     * @return - list of supported file extensions by the file handler
     */
    List<String> getSupportedExtensions();

    default boolean isExtensionSupported(String ext) {
        if (ext.startsWith("."))
            ext = ext.substring(1);
        return getSupportedExtensions().contains(ext.toLowerCase());
    }

    /**
     * Load a file to file cache
     */
    void loadFile(File file);

    IParser<E> getParser();

    /**
     * @return - Manager to cast types to serialized values and back
     */
    TypeManager getTypeManager();

    /**
     * Initializes the file handler. Should always be called for each handler before actual use
     */
    void init();

    /**
     * Process a configurable field. If a file's value exists it will be loaded to the given field, if not exist it will
     * be generated.
     *
     * @param field - field to be written
     * @param owner - the actual owner of the field. Can be a class for static fields or an object for object-property
     *              fields
     * @param file - the file to be read and/or generated
     */
    void loadField(Field field, Object owner, File file);

    /**
     * @return - the file cache to be filled and used while config loading. Will be cleared after loading
     */
    Map<File, FileCache<E>> getFileCache();

    /**
     * @return - storage where the missing configs will be stored and used for generation. Will be cleared after loading
     */
    Map<File, List<GenData<E>>> getGenerateStorage();

    /**
     * @return - Element type that the file handler works with
     */
    Class<E> getElementType();

    /**
     * Generation processing
     */
    void generateMissing();

    /**
     * Final clean up
     */
    void cleanup();
}
