package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.cast.ITypeCaster;
import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.model.file.AbstractFileHandler;
import com.hackness.cfg4j.core.model.file.FileCache;
import com.hackness.cfg4j.xml.type.IXmlTypeCaster;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hack
 * Date: 25-Nov-19 11:57
 */
@Slf4j
public class XmlFileHandler extends AbstractFileHandler<Element> {
    @Getter(lazy = true) private static final XmlFileHandler instance = new XmlFileHandler();

    @Getter private final XmlParser parser = new XmlParser();
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
    public Class<Element> getElementType() {
        return Element.class;
    }

    @Override
    protected String typeCastersPackage() {
        return "com.hackness.cfg4j.xml.type";
    }

    @Override
    protected Class<? extends ITypeCaster> typeCasterInterface() {
        return IXmlTypeCaster.class;
    }
}
