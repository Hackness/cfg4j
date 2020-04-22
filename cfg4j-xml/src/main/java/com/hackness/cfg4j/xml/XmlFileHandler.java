package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.file.FileCache;
import com.hackness.cfg4j.core.model.file.IFileHandler;
import com.hackness.cfg4j.core.parse.IParser;
import com.hackness.cfg4j.xml.type.IXmlTypeCaster;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Hack
 * Date: 25-Nov-19 11:57
 */
@Slf4j
public class XmlFileHandler implements IFileHandler {
    @Getter(lazy = true) private static final XmlFileHandler instance = new XmlFileHandler();

    private final XmlParser parser = new XmlParser();
    @Getter private final TypeManager typeManager = new TypeManager();
    @Getter private final Map<File, FileCache<Element>> fileCache = new HashMap<>();
    @Getter private final Map<File, List<Element>> generateStorage = new HashMap<>();

    @Override
    public List<String> getSupportedExtensions() {
        return Collections.singletonList("xml");
    }

    @Override
    public void loadFile(File file) {
        getParser().process(file);
    }

    @Override
    public IParser<Element> getParser() {
        return parser;
    }

    @Override
    public void init() {
        Reflections r = new Reflections("com.hackness.cfg4j.xml.type");
        r.getSubTypesOf(IXmlTypeCaster.class).forEach(caster -> {
            try {
                typeManager.registerTypeCaster(caster.newInstance());
            } catch (Exception e) {
                log.error("Failed  to initialize xml caster " + caster, e);
            }
        });
    }

    @Override
    public void loadField(Field field, Object owner, File file) {
        FileCache<Element> fileData = fileCache.get(file);
        Element element = null;
        if (fileData != null) {
            element = fileData.getElements().get(field.getName());
        }
        if (!field.isAccessible())
            field.setAccessible(true);
        if (element == null) {
            log.warn("Value for field {} wasn't found and will be generated", field);
            Element genElement = null;
            try {
                genElement = typeManager.serialize(field, owner, Element.class);
            } catch (Exception e) {
                log.error("Failed to generate config filed: " + field, e);
            }
            generateStorage
                    .computeIfAbsent(file, f -> new ArrayList<>())
                    .add(genElement);
        } else {
            try {
                Object val = typeManager.deserialize(element, field.getGenericType(), field);
                field.set(owner, val);
            } catch (Exception e) {
                log.error("Failed to load config filed: " + field, e);
            }
        }
    }
}
