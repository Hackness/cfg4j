package com.hackness.cfg4j.core.parse;

import com.hackness.cfg4j.core.ConfigLoader;
import com.hackness.cfg4j.core.anno.Cfg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Hack
 * Date: 10-Nov-19 23:53
 */
@Slf4j
public abstract class AbstractTypeHandler<T> implements ITypeHandler<T> {
    public void deserialize(Class<?> owner, String fieldName, T node) {
        Field field = preProcessField(owner, fieldName);
        if (field == null)
            return;
        processField(owner, field, node);
    }

    protected abstract void processField(Class<?> owner, Field field, T node);

    /**
     * @return - required filed object or null if the field is not valid
     */
    protected Field preProcessField(Class<?> owner, String fieldName) {
        Field field = FieldUtils.getField(owner, fieldName, true);
        if (field == null) {
            log.warn("File contains an unknown option: {}, appropriate field wasn't found. File: {}, Class: {}",
                    fieldName, ConfigLoader.getInstance().getConfigFile(owner), owner);
            return null;
        }
        if (!field.isAnnotationPresent(Cfg.class)) {
            log.warn("Field [{}] has no @Cfg annotation, value ignored. Class: {}", field, owner);
            return null;
        }
        if (!Modifier.isStatic(field.getModifiers())) {
            log.warn("Attempt to load non-static field for class initialization. If you want to initialize an " +
                    "instance, please, use instanceOnly option for the class annotation. Class: {}, Field: {}",
                    owner, fieldName);
            return null;
        }
        return field;
    }
}
