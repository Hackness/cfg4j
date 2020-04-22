package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.file.FileCache;
import com.hackness.cfg4j.core.parse.AbstractParser;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:43
 */
@Slf4j
public class XmlParser extends AbstractParser<Element> {
    private static final SAXBuilder saxBuilder = new SAXBuilder();

    @Override
    protected Element build(File file) throws Exception {
        return saxBuilder.build(file).getRootElement();
    }

    @Override
    public void parse(Element root) {
        Map<String, Element> configMap = root.getChildren()
                .stream()
                .collect(Collectors.toMap(e -> XmlUtil.getNonNullAttribute(e, "name"), e -> e));
        FileCache<Element> fileCache = new FileCache<>(getParsingFile(), configMap);
        XmlFileHandler.getInstance().getFileCache().put(getParsingFile(), fileCache);
    }
}
