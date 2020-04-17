package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.cast.ITypeCaster;
import com.hackness.cfg4j.xml.XmlUtil;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by Hack
 * Date: 17-Apr-20 05:08
 */
public interface IXmlTypeCaster<O> extends ITypeCaster<Element, O> {
    default Class<Element> getElementType() {
        return Element.class;
    }

    default Element namedElement(Field field) {
        Element element = new Element(serializedDescriptor());
        if (field != null)
            element.setAttribute("name", field.getName());
        return element;
    }

    default Element simpleElement(Field field, Object value) {
        return namedElement(field).setAttribute("value", Objects.toString(value));
    }

    default <C> C simpleCast(Function<String, C> func, Element element) {
        return func.apply(XmlUtil.getNonNullAttribute(element, "value"));
    }
}
