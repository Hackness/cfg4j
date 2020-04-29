package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.model.GenData;
import com.hackness.cfg4j.core.model.file.FileCache;
import com.hackness.cfg4j.core.parse.AbstractParser;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Comment;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:43
 */
@Slf4j
public class XmlParser extends AbstractParser<Element> {
    private static final SAXBuilder saxBuilder = new SAXBuilder();
    private static final XMLOutputter xmlOutput = new XMLOutputter();
    static {
        xmlOutput.setFormat(Format.getPrettyFormat());
    }

    @Override
    protected Element build(File file) throws Exception {
        return saxBuilder.build(file).getRootElement();
    }

    @Override
    public void parse(Element root) {
        Map<String, Element> configMap = root.getChildren()
                .stream()
                .collect(Collectors.toMap(e -> XmlUtil.getNonNullAttribute(e, "name"), e -> e));
        FileCache<Element> fileCache = new FileCache<>(getParsingFile(), configMap, root);
        XmlFileHandler.getInstance().getFileCache().put(getParsingFile(), fileCache);
    }

    public void generate(File file, Element root, List<GenData<Element>> add) {
        Element rootOut = root == null ? new Element("config") : root;
        add.forEach(genData -> {
            String cmt = genData.getComment();
            if (!cmt.isEmpty())
                rootOut.addContent(new Comment(cmt));
            rootOut.addContent(genData.getElement());
        });
        FileWriter fw = null;
        try {
            Files.createDirectories(Paths.get(file.getParent()));
            fw = new FileWriter(file);
            xmlOutput.output(rootOut, fw);
        } catch (IOException e) {
            log.error("Filed  to write " + file, e);
        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    log.error("Filed to close stream " + file, e);
                }
        }
    }
}
