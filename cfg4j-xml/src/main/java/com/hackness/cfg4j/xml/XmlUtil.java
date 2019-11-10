package com.hackness.cfg4j.xml;

import org.jdom2.Element;

import java.util.Optional;

/**
 * Created by Hack
 * Date: 11-Nov-19 01:04
 */
public class XmlUtil {
    public static String getNonNullAttribute(Element element, String valName) {
        return Optional.ofNullable(element.getAttributeValue(valName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Element '%s' must have a parameter '%s'",
                        element.getName(), valName)));
    }

    public static Element getNonNullChild(Element element, String childName) {
        return Optional.ofNullable(element.getChild(childName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Element '%s' must have a child '%s'",
                        element.getName(), childName)));
    }

}
