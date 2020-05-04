package model.read;

import com.hackness.cfg4j.core.anno.Cfg;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hack
 * Date: 17-Apr-20 08:15
 */
public class ConfiguringClass {
    private static final String CFG_FILE = "src/test/resources/read/test.xml";
    @Cfg(file = CFG_FILE) public static Integer intVal;
    @Cfg(file = CFG_FILE) public static Boolean boolVal;
    @Cfg(file = CFG_FILE) public static List<Integer> intList;
    @Cfg(file = CFG_FILE) @Getter private static Double privateDouble;
    @Cfg(file = CFG_FILE) public static Map<String, Double> mapStrDbl;
    @Cfg(file = CFG_FILE) public static Set<Integer> intSet;
    @Cfg(file = CFG_FILE) public static int rawInt;
    @Cfg(file = CFG_FILE) public static Map<String, List<Integer>> mapStringListInt;
}
