package com.hackness.cfg4j.core.cast;

import com.hackness.cfg4j.core.Util;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 27-Nov-19 08:00
 */
public class TypeManagerTest {
    private static TypeManager typeManager = new TypeManager();

    private static class StringIntCaster implements ITypeCaster<String, Integer> {
        @Override
        public String serializedDescriptor() {
            return "num";
        }

        @Override
        public String serialize(Integer obj, Type type, Field field) {
            return Integer.toString(obj);
        }

        @Override
        public Integer deserialize(String element, Type type, Field field) {
            return Integer.parseInt(element);
        }

        @Override
        public Class<String> getElementType() {
            return String.class;
        }

        @Override
        public Class<Integer> getObjectType() {
            return Integer.class;
        }

        @Override
        public Integer emptyObjectInstance() {
            return 0;
        }
    }

    private static class StringRawIntCaster extends StringIntCaster {
        @Override
        public Class<Integer> getObjectType() {
            return int.class;
        }
    }

    private static class StringListCaster implements ITypeCaster<String, List> {
        @Override
        public String serializedDescriptor() {
            return "list";
        }

        @Override
        public String serialize(List obj, Type type, Field field) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Type castType = Util.getGenericTypes(type)[0];
            obj.forEach(el -> {
                sb.append(typeManager.serialize(el, castType, getElementType(), field)).append(";");
            });
            if (obj.size() > 0)
                sb.setLength(sb.length() - 1); // cut last delim
            sb.append("]");
            return sb.toString();
        }

        @Override
        public List deserialize(String element, Type type, Field field) throws Exception {
            Type castType = Util.getGenericTypes(type)[0];
            Class rawType = (Class) Util.getRawType(type);
            List<String> splitData = splitData(element);
            Constructor constructor = null;
            try {
                constructor = rawType.getConstructor();
            } catch (Exception e) {
                // no default accessible constructor found
            }
            List out;
            if (constructor != null)
                out = (List) constructor.newInstance();
            else
                out = emptyObjectInstance();
            for (String data : splitData) {
                Object castedValue = typeManager.deserialize(data, castType, field);
                out.add(castedValue);
            }
            return out;
        }

        @Override
        public Class<String> getElementType() {
            return String.class;
        }

        @Override
        public Class<List> getObjectType() {
            return List.class;
        }

        @Override
        public List emptyObjectInstance() {
            return new ArrayList();
        }

        private List<String> splitData(String element) {
            final char startSeq = '[';
            final char endSeq = ']';
            final char delim = ';';
            int start = element.indexOf(startSeq);
            int end = element.lastIndexOf(endSeq);
            String listContent = element.substring(start + 1, end);
            int level = 0;
            List<String> splitData = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < listContent.length(); i++) {
                char sym = listContent.charAt(i);
                if (sym == startSeq)
                    level++;
                else if (sym == endSeq)
                    level--;
                else if (sym == delim && level == 0) {
                    splitData.add(sb.toString());
                    sb.setLength(0);
                    continue;
                }
                sb.append(sym);
            }
            splitData.add(sb.toString());
            return splitData;
        }
    }

    public List<Integer> list;

    @Test
    public void strListDeserialize() throws Exception {
        typeManager.registerTypeCaster(new StringListCaster());
        typeManager.registerTypeCaster(new StringIntCaster());
        String intList = "[23;14;22;15]";
        list = (List<Integer>) typeManager
                .deserialize(intList, getClass().getField("list").getGenericType(), "list", null);
        Assert.assertEquals((int) list.get(3), 15);
    }

    public List<List<Integer>> listInList;

    @Test
    public void strListInListDeserialize() throws Exception {
        typeManager.registerTypeCaster(new StringListCaster());
        typeManager.registerTypeCaster(new StringIntCaster());
        String intList = "[[1;2;3];[4;5;6];[7;8;9]]";
        listInList = (List<List<Integer>>) typeManager
                .deserialize(intList, getClass().getField("listInList").getGenericType(), "list", null);
        Assert.assertEquals((int) listInList.get(2).get(0), 7);
    }

    @Test
    public void strListFieldWrite() throws Exception {
        typeManager.registerTypeCaster(new StringListCaster());
        typeManager.registerTypeCaster(new StringIntCaster());
        String intList = "[23;14;22;15]";
        Field listField = getClass().getField("list");
        Object val = typeManager.deserialize(intList, listField.getGenericType(), "list", null);
        listField.set(this, val);
        Assert.assertEquals((int) list.get(2), 22);
    }

    public Integer objInt = 0;
    public int rawInt = 0;
    @Test
    public void objAndRawFieldWrite() throws Exception {
        typeManager.registerTypeCaster(new StringIntCaster());
        typeManager.registerTypeCaster(new StringRawIntCaster());
        String strVal = "23";
        Field objField = getClass().getField("objInt");
        Field rawField = getClass().getField("rawInt");
        Object objVal = typeManager.deserialize(strVal, objField.getGenericType(), null);
        Object rawVal = typeManager.deserialize(strVal, rawField.getGenericType(), null);
        objField.set(this, objVal);
        rawField.set(this, rawVal);
        Assert.assertTrue(objInt == 23);
        Assert.assertEquals(rawInt, 23);
    }

    public List<Integer> serList;
    @Test
    public void strListSerialize() throws Exception {
        typeManager.registerTypeCaster(new StringListCaster());
        typeManager.registerTypeCaster(new StringIntCaster());
        serList = new ArrayList<>();
        serList.add(1);
        serList.add(2);
        serList.add(3);
        String serialized = typeManager
                .serialize(serList, getClass().getField("serList").getGenericType(), String.class, null);
        Assert.assertEquals(serialized, "[1;2;3]");
    }

    public List<List<Integer>> serListInList;
    @Test
    public void strListInListSerialize() throws Exception {
        typeManager.registerTypeCaster(new StringListCaster());
        typeManager.registerTypeCaster(new StringIntCaster());
        serListInList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                list.add(i * 3 + j);
            }
            serListInList.add(list);
        }
        String serialized = typeManager
                .serialize(serListInList, getClass().getField("serListInList").getGenericType(), String.class, null);
        Assert.assertEquals(serialized, "[[0;1;2];[3;4;5];[6;7;8]]");
    }
}
