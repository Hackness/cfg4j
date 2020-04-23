package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.Util;
import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.type.MapCaster;
import com.hackness.cfg4j.xml.XmlFileHandler;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Hack
 * Date: 17-Apr-20 07:21
 */
public class XmlMapCaster extends MapCaster<Element> implements IXmlTypeCaster<Map> {
    private TypeManager typeManager = XmlFileHandler.getInstance().getTypeManager();

    @Override
    public Element serialize(Map obj, Type type, Field field) {
        Element eMap = namedElement(field);
        Type keyType = Util.getGenericTypes(type)[0];
        Type valueType = Util.getGenericTypes(type)[1];
        if (obj != null)
            obj.forEach((k, v) -> {
                Element eKey = typeManager.serialize(k, keyType, getElementType(), null);
                Element eVal = typeManager.serialize(v, valueType, getElementType(), null);
                eKey.addContent(eVal); //TODO: make simple types in one element with key + value attributes
                eMap.addContent(eKey);
            });
        return eMap;
    }

    @Override
    public Map deserialize(Element element, Type type, Field field) throws Exception {
        Type keyType = Util.getGenericTypes(type)[0];
        Type valueType = Util.getGenericTypes(type)[1];
        Class rawType = (Class) Util.getRawType(type);
        Map out = newInstance(type);
        for (Element e : element.getChildren()) {
            Object key = typeManager.deserialize(e, keyType, null);
            Object val = typeManager.deserialize(e.getChildren().get(0), valueType, null);
            out.put(key, val);
        }
        return out;
    }
}
