package com.hackness.cfg4j.xml.type;

import com.hackness.cfg4j.core.Util;
import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.type.ListCaster;
import com.hackness.cfg4j.xml.XmlFileHandler;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Hack
 * Date: 17-Apr-20 05:30
 */
@Slf4j
public class XmlListCaster extends ListCaster<Element> implements IXmlTypeCaster<List> {
    private TypeManager typeManager = XmlFileHandler.getInstance().getTypeManager();

    @Override
    public Element serialize(List obj, Type type, Field field) {
        Element eList = namedElement(field);
        Type castType = Util.getGenericTypes(type)[0];
        obj.forEach(o -> {
            eList.addContent(typeManager.serialize(o, castType, getElementType(), null));
        });
        return eList;
    }

    @Override
    public List deserialize(Element element, Type type, Field field) throws Exception {
        Type castType = Util.getGenericTypes(type)[0];
        List out = newInstance(type);
        for (Element e : element.getChildren()) {
            out.add(typeManager.deserialize(e, castType, null));
        }
        return out;
    }
}
