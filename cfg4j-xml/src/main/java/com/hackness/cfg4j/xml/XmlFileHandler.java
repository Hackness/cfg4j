package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.model.file.IFileHandler;
import com.hackness.cfg4j.core.parse0.IParser;
import org.jdom2.Element;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hack
 * Date: 25-Nov-19 11:57
 */
public class XmlFileHandler implements IFileHandler {
    private final XmlParser parser = new XmlParser();

    @Override
    public List<String> getSupportedExtensions() {
        return Collections.singletonList("xml");
    }

    @Override
    public void deserialize(File file, Object owner) {
        getParser().process(file, owner);
    }

    @Override
    public IParser<Element> getParser() {
        return parser;
    }
}
