import com.hackness.cfg4j.core.anno.Cfg;
import com.hackness.cfg4j.core.anno.CfgClass;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hack
 * Date: 17-Apr-20 08:15
 */
@CfgClass(value = "Test configuration file", filePath = "test.xml")
public class ConfiguringClass {
    @Cfg public static Integer intVal;
    @Cfg public static Boolean boolVal;
    @Cfg public static List<Integer> intList;
    @Cfg @Getter private static Double privateDouble;
    @Cfg public static Map<String, Double> mapStrDbl;
    @Cfg public static Set<Integer> intSet;
    @Cfg public static int rawInt;
    @Cfg public static Map<String, List<Integer>> mapStringListInt;
}
