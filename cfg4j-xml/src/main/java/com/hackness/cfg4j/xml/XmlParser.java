package com.hackness.cfg4j.xml;

import com.hackness.cfg4j.core.anno.Cfg;
import com.hackness.cfg4j.core.cast.TypeManager;
import com.hackness.cfg4j.core.parse.AbstractParser;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:43
 */
@Slf4j
public class XmlParser extends AbstractParser<Element> {
    private static final SAXBuilder saxBuilder = new SAXBuilder();

    @Override
    protected Element build(File file) throws Exception {
        return saxBuilder.build(file).getRootElement();
    }

    @Override
    public void parse(Element root, Object owner) {
        Map<String, Element> configMap = root.getChildren()
                .stream()
                .collect(Collectors.toMap(e -> XmlUtil.getNonNullAttribute(e, "name"), e -> e));
        Class ownerClass = getOwnerType(owner);
        List<Field> generationList = new ArrayList<>(); // TODO: config generation
        Stream.of(ownerClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Cfg.class))
                .forEach(field -> {
                    //TODO: field preprocess with checks
                    Element e = configMap.get(field.getName());
                    if (e == null) {
                        log.info("Option {} wasn't found in config file of {} and will be generated.",
                                field.getName(), ownerClass);
                        generationList.add(field);
                        return;
                    }
                    Object castedValue;
                    try {
                        castedValue = TypeManager.getInstance().deserialize(e, field.getGenericType(), e.getName());
                    } catch (Exception ex) {
                        log.warn("An error occurred while parsing value '{}', field: {}, owner: {}",
                                e, field, owner);
                        return;
                    }
                    if (!field.isAccessible())
                        field.setAccessible(true);
                    try {
                        field.set(owner, castedValue);
                    } catch (Exception ex) {
                        log.warn("Failed to write field {} of {}. Value: {}", field, owner, castedValue);
                    }
                });

    }

    protected Class getOwnerType(Object owner) {
        Class<?> type = owner.getClass();
        return type == Class.class ? (Class) owner : type;
    }


}
