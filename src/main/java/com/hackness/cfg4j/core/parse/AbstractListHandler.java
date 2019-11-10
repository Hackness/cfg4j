package com.hackness.cfg4j.core.parse;

import com.hackness.cfg4j.core.Util;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Hack
 * Date: 11-Nov-19 00:37
 *
 * TODO: castValue(), AbstractRawHandler, ConfigParser to find and get appropriate handler (raw handler)
 */
public abstract class AbstractListHandler<T> extends AbstractTypeHandler<T> {

    protected abstract List<String> parseList(T node);

    @Override
    public String getNodeName() {
        return "option_list";
    }

    @Override
    protected void processField(Class<?> owner, Field field, T node) {
        Class type = Util.getGenericType(field, 0);
        parseList(node).forEach(val -> {
//            ((List) field.get(owner)).add(); //castValue()
        });
    }
}
