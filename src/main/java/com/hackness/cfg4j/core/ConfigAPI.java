package com.hackness.cfg4j.core;

import com.hackness.cfg4j.core.anno.CfgClass;
import com.hackness.cfg4j.core.model.file.IFileHandler;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 10-Nov-19 20:40
 *
 * TODO: consistence check. Manual no-update flags for classes to not call config updates while extensive processes.
 * mgr.noUpdate(this, true);
 * doSomething()
 * mgr.noUpdate(this, false);
 * Config update will be placed in queue and should be called when noUpdate flag is false
 */
public class ConfigAPI {
    @Getter(lazy = true) private static final ConfigAPI instance = new ConfigAPI();
    private List<IFileHandler> fileHandlers = new ArrayList<>();
    private String configRoot = "";

    public File getConfigFile(Class<?> clazz) {
        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

    public void registerFileHandler(IFileHandler handler) {
        fileHandlers.add(handler);
    }

    public void init() {
        fileHandlers.forEach(IFileHandler::init);
    }

    public void loadFile(File file, Object owner) {
        if (file == null)
            throw new NullPointerException("Null config file");
        String ext = Util.getFileExtension(file);
        if (ext.isEmpty())
            throw new IllegalArgumentException("File extension is not set and no file handler was specified. " +
                    "Appropriate handler cannot be selected.");
        IFileHandler handler = fileHandlers.stream()
                .filter(h -> h.isExtensionSupported(ext))
                .findAny()
                .orElseThrow(() -> new NullPointerException("No appropriate handler found for extension " + ext));
        handler.loadFile(file, owner);
    }

    public void loadFile(File file, IFileHandler handler, Object owner) {
        if (file == null)
            throw new NullPointerException("Null config file");
        if (handler == null)
            throw new NullPointerException("Handler is null");
        handler.loadFile(file, owner);
    }

    public void loadClass(Class configurableClass) {
        if (configurableClass == null)
            throw new NullPointerException("Null class load attempt");
        if (!configurableClass.isAnnotationPresent(CfgClass.class))
            throw new IllegalArgumentException("Attempt to load a class which isn't marked with CfgClass annotation. " +
                    "Class: " + configurableClass);
        File file = getConfigFile(configurableClass);
        if (!file.exists())
            throw new NullPointerException("Config file for class " + configurableClass + " wasn't found in: " + file);
        loadFile(file, configurableClass);
    }
}
