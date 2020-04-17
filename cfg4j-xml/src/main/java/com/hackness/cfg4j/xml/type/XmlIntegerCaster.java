package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.IntegerCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 05:28
 */
public class XmlIntegerCaster extends IntegerCaster<Element> implements IXmlTypeCaster<Integer> {
    @Override
    public Element serialize(Integer obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public Integer deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(Integer::parseInt, element);
    }
}
