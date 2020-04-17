package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.RawShortCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 11:07
 */
public class XmlRawShortCaster extends RawShortCaster<Element> implements IXmlTypeCaster<Short> {
    @Override
    public Element serialize(Short obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public Short deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(Short::parseShort, element);
    }
}
