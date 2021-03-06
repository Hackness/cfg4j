package com.hackness.cfg4j.core;

import com.hackness.cfg4j.core.anno.Cfg;
import com.hackness.cfg4j.core.anno.CfgClass;
import com.hackness.cfg4j.core.model.file.IFileHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.reflections.util.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Hack
 * Date: 22-Apr-20 05:00
 *
 * TODO: consistence check. Manual no-update flags for classes to not call config updates while extensive processes.
 * mgr.noUpdate(this, true);
 * doSomething()
 * mgr.noUpdate(this, false);
 * Config update will be placed in queue and should be called when noUpdate flag is false
 *
 * TODO: @CfgClass support
 * TODO: comment line split
 */
@Slf4j
public class ConfigAPI {
    @Getter(lazy = true) private static final ConfigAPI instance = new ConfigAPI();
    private List<IFileHandler> fileHandlers = new ArrayList<>();
    private Set<File> files = new HashSet<>();
    private Set<String> configurablePackages = new HashSet<>();
    private AtomicBoolean initialized = new AtomicBoolean(false);

    public void registerFileHandler(IFileHandler handler) {
        fileHandlers.add(handler);
    }

    public void addFilePath(File file) {
        files.add(file);
    }

    public void addFilePath(String path) {
        files.add(new File(path));
    }

    public void addConfigurablePackage(String pkg) {
        configurablePackages.add(pkg);
    }

    public synchronized void load() {
        log.info("Config loading started");
        long start = System.currentTimeMillis();

        if (initialized.compareAndSet(false, true)) {
            fileHandlers.forEach(IFileHandler::init);
        }
        loadFiles();
        loadPackages();
        fileHandlers.forEach(IFileHandler::generateMissing);
        fileHandlers.forEach(IFileHandler::cleanup);

        long finish = System.currentTimeMillis() - start;
        log.info("Config loading finished. Taken time: {} ms", finish);
    }

    public void loadField(Field field, Object owner) {
        if (field == null)
            throw new NullPointerException("Field cannot be null");
        if (owner == null)
            throw new NullPointerException("Field owner cannot be null");
        Cfg cfgAnno = field.getAnnotation(Cfg.class);
        if (cfgAnno == null)
            throw new IllegalArgumentException("Field " + field + " has no @Cfg annotation");
        String filePath = cfgAnno.file();
        if (filePath.isEmpty() && !cfgAnno.instanceLoad()) {
            CfgClass clsAnno = (CfgClass) Util.getOwnerType(owner).getAnnotation(CfgClass.class);
            if (clsAnno == null)
                throw new IllegalArgumentException("Field " + field + " has no specified file and its class has no " +
                        "CfgClass annotation present. File is not set");
            filePath = clsAnno.value();
            if (filePath.isEmpty())
                throw new IllegalArgumentException("File for field " + field + " was not specified neither in @Cfg " +
                        "of the field nor in @CfgClass of the class");
        }
        File cfgFile = new File(filePath);
        loadField(field, owner, cfgFile);
    }

    private void loadField(Field field, Object owner, File file) {
        String ext = Util.getFileExtension(file);
        if (ext.isEmpty())
            throw new IllegalArgumentException("File extension is not set and no file handler was specified. " +
                    "Appropriate handler cannot be selected.");
        IFileHandler handler = fileHandlers.stream()
                .filter(h -> h.isExtensionSupported(ext))
                .findAny()
                .orElseThrow(() -> new NullPointerException("No appropriate handler found for extension " + ext));
        handler.loadField(field, owner, file);
    }

    private void loadFiles() {
        files.forEach(file -> {
            Path path = file.toPath();
            if (Files.isDirectory(path)) {
                try {
                    Files.walk(path)
                            .filter(Files::isRegularFile)
                            .forEach(p -> loadFile(p.toFile()));
                } catch (IOException e) {
                    log.error("Failed to read " + file, e);
                }
            } else {
                loadFile(file);
            }
        });
    }

    private void loadFile(File f) {
        if (!Files.exists(f.toPath()))
            return;
        fileHandlers.stream()
                .filter(h -> h.isExtensionSupported(Util.getFileExtension(f)))
                .findAny().ifPresent(handler -> handler.loadFile(f));
    }

    //TODO: split
    private void loadPackages() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        List<URL> urls = new ArrayList<>();
        FilterBuilder filter = new FilterBuilder();
        configurablePackages.forEach(pkg -> {
            urls.addAll(ClasspathHelper.forPackage(pkg));
            filter.includePackage(pkg);
        });
        cb.setUrls(urls);
        cb.filterInputsBy(filter);
        cb.setScanners(new FieldAnnotationsScanner());
        Reflections r = new Reflections(cb);
        r.getStore().get(FieldAnnotationsScanner.class, Cfg.class.getName()).forEach(str -> {
            try {
                Field field = Utils.getFieldFromString(str, r.getConfiguration().getClassLoaders());
                Class cls = Class.forName(str.substring(0, str.lastIndexOf('.')));
                Cfg anno = field.getAnnotation(Cfg.class);
                if (anno.instanceLoad()) {
                    if (!field.isAccessible())
                        field.setAccessible(true);
                    Object owner = field.get(cls);
                    if (owner == null) {
                        log.error("Field {} was set as instanceLoad but its value is null", field);
                        return;
                    }
                    Arrays.stream(owner.getClass().getDeclaredFields())
                            .filter(f -> f.getAnnotation(Cfg.class) != null)
                            .forEach(f -> {
                                loadField(f, owner);
                            });
                } else {
                    if (!Modifier.isStatic(field.getModifiers()))
                        return;
                    loadField(field, cls);
                }
            } catch (Exception e) {
                log.error("Failed to process field " + str, e);
            }
        });
    }
}
