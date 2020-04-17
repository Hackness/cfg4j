package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.RawDoubleCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 11:02
 */
public class XmlRawDoubleCaster extends RawDoubleCaster<Element> implements IXmlTypeCaster<Double> {
    @Override
    public Element serialize(Double obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public Double deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(Double::parseDouble, element);
    }
}
