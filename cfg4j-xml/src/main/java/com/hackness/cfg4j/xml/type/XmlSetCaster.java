package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.Util;
import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.type.SetCaster;
import com.hackness.cfg4j.xml.XmlFileHandler;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by Hack
 * Date: 17-Apr-20 11:42
 */
public class XmlSetCaster extends SetCaster<Element> implements IXmlTypeCaster<Set> {
    private TypeManager typeManager = XmlFileHandler.getInstance().getTypeManager();

    @Override
    public Element serialize(Set obj, Type type, Field field) {
        Element eSet = namedElement(field);
        Type castType = Util.getGenericTypes(type)[0];
        if (obj != null)
            obj.forEach(o -> {
                eSet.addContent(typeManager.serialize(o, castType, getElementType(), null));
            });
        return eSet;
    }

    @Override
    public Set deserialize(Element element, Type type, Field field) throws Exception {
        Type castType = Util.getGenericTypes(type)[0];
        Set out = newInstance(type);
        for (Element e : element.getChildren()) {
            out.add(typeManager.deserialize(e, castType, null));
        }
        return out;
    }
}
