package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.StringCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 10:12
 */
public class XmlStringCaster extends StringCaster<Element> implements IXmlTypeCaster<String> {
    @Override
    public Element serialize(String obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public String deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(s -> s, element);
    }
}
