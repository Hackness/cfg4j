package com.hackness.cfg4j.core.model.file;

import com.hackness.cfg4j.core.anno.Cfg;
import com.hackness.cfg4j.core.cast.ITypeCaster;
import com.hackness.cfg4j.core.model.GenData;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 22-Apr-20 10:51
 */
@Slf4j
public abstract class AbstractFileHandler<E> implements IFileHandler<E> {

    //TODO: split the method
    @Override
    public void loadField(Field field, Object owner, File file) {
        FileCache<E> fileData = getFileCache().get(file);
        E element = null;
        if (fileData != null) {
            element = fileData.getElements().get(field.getName());
        }
        if (!field.isAccessible())
            field.setAccessible(true);
        if (element == null) {
            log.info("Value for field [{}] wasn't found and will be generated", field);
            E genElement = null;
            try {
                genElement = getTypeManager().serialize(field, owner, getElementType());
            } catch (Exception e) {
                log.error("Failed to generate config filed: " + field, e);
            }
            List<GenData<E>> eList = getGenerateStorage().computeIfAbsent(file, f -> new ArrayList<>());
            String strCmt = field.getAnnotation(Cfg.class).value();
            eList.add(new GenData<>(genElement, strCmt));
        } else {
            try {
                Object val = getTypeManager().deserialize(element, field.getGenericType(), field);
                field.set(owner, val);
            } catch (Exception e) {
                log.error("Failed to load config filed: " + field, e);
            }
        }
    }

    @Override
    public void init() {
        Reflections r = new Reflections(typeCastersPackage());
        r.getSubTypesOf(typeCasterInterface()).forEach(caster -> {
            try {
                getTypeManager().registerTypeCaster(caster.newInstance());
            } catch (Exception e) {
                log.error("Failed  to initialize xml caster " + caster, e);
            }
        });

    }

    @Override
    public void generateMissing() {
        getGenerateStorage().forEach((file, list) -> {
            FileCache<E> cache = getFileCache().get(file);
            E root = null;
            if (cache != null)
                root = cache.getRoot();
            getParser().generate(file, root, list);
        });
    }

    @Override
    public void cleanup() {
        getFileCache().clear();
        getGenerateStorage().clear();
    }

    protected abstract String typeCastersPackage();

    protected abstract Class<? extends ITypeCaster> typeCasterInterface();
}
