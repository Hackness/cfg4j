package model.generate;

import com.hackness.cfg4j.core.anno.Cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hack
 * Date: 23-Apr-20 07:50
 */
public class GenerationClass {
    private static final String CFG_FILE = "src/test/resources/generate/generation.xml";
    @Cfg(file = CFG_FILE, value = "Integer value description comment")
    public static Integer intVal;
    @Cfg(file = CFG_FILE, value = "Boolean value description comment")
    public static Boolean boolVal = true;
    @Cfg(file = CFG_FILE, value = "Null list description comment")
    public static List<Integer> intList;
    @Cfg(file = CFG_FILE, value = "Long comment, long comment, long comment, long comment, " +
            "long comment, long comment, long comment, long comment, long comment, long comment, " +
            "long comment, long comment, long comment, long comment, long comment, long comment, ")
    public static int rawInt = 3;
    @Cfg(file = CFG_FILE)
    public static Map<String, Integer> strIntMap = new HashMap<>();
    static {
        strIntMap.put("a", 1);
        strIntMap.put("b", 2);
        strIntMap.put("c", 3);
    }
}
