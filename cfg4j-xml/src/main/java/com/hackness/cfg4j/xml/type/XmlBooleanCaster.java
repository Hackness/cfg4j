package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.type.BooleanCaster;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by Hack
 * Date: 17-Apr-20 04:32
 */
public class XmlBooleanCaster extends BooleanCaster<Element> implements IXmlTypeCaster<Boolean> {
    @Override
    public Element serialize(Boolean obj, Type type, Field field) {
        return simpleElement(field, obj);
    }

    @Override
    public Boolean deserialize(Element element, Type type, Field field) throws Exception {
        return simpleCast(Boolean::parseBoolean, element);
    }

}
