package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.parse.AbstractListHandler;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 10-Nov-19 21:27
 */
public class XmlListHandler extends AbstractListHandler<Element> {
    @Override
    protected List<String> parseList(Element node) {
        List<String> list = new ArrayList<>();
        node.getChildren("set").forEach(set -> list.add(XmlUtil.getNonNullAttribute(set, "value")));
        return list;
    }
}
