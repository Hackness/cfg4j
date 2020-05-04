package model.object;

import com.hackness.cfg4j.core.anno.Cfg;
import com.hackness.cfg4j.core.anno.CfgClass;
import lombok.Data;

/**
 * Created by Hack
 * Date: 04-May-20 19:49
 */
@Data
@CfgClass("src/test/resources/object/obj.xml")
public class CfgObject {
    @Cfg("Integer field")
    private int intField = 3;

    @Cfg("String field")
    private String strField = "123";
}
