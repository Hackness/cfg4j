package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.LongCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 07:20
 */
public class XmlLongCaster extends LongCaster<Element> implements IXmlTypeCaster<Long> {
    @Override
    public Element serialize(Long obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public Long deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(Long::parseLong, element);
    }
}
