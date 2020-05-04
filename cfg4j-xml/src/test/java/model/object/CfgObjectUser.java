package model.object;

import com.hackness.cfg4j.core.anno.Cfg;
import lombok.Getter;

/**
 * Created by Hack
 * Date: 04-May-20 20:52
 */
public class CfgObjectUser {
    @Cfg(value = "Object user. The description will not be shown", instanceLoad = true)
    @Getter private static CfgObject cfgObject = new CfgObject();
}
