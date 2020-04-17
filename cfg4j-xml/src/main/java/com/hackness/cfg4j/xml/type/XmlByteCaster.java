package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.ByteCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 05:25
 */
public class XmlByteCaster extends ByteCaster<Element> implements IXmlTypeCaster<Byte> {
    @Override
    public Element serialize(Byte obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public Byte deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(Byte::parseByte, element);
    }
}
