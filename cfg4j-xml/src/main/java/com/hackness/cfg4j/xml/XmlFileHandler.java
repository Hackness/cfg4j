package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.model.file.IFileHandler;
import com.hackness.cfg4j.core.parse.IParser;
import com.hackness.cfg4j.xml.type.IXmlTypeCaster;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.reflections.Reflections;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hack
 * Date: 25-Nov-19 11:57
 */
@Slf4j
public class XmlFileHandler implements IFileHandler {
    @Getter(lazy = true) private static final XmlFileHandler instance = new XmlFileHandler();

    private final XmlParser parser = new XmlParser();
    @Getter private final TypeManager typeManager = new TypeManager();

    @Override
    public List<String> getSupportedExtensions() {
        return Collections.singletonList("xml");
    }

    @Override
    public void loadFile(File file, Object owner) {
        getParser().process(file, owner);
    }

    @Override
    public IParser<Element> getParser() {
        return parser;
    }

    @Override
    public TypeManager getTypeManager() {
        return typeManager;
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
}
