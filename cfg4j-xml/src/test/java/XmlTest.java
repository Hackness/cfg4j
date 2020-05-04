import com.hackness.cfg4j.core.ConfigAPI;
import com.hackness.cfg4j.xml.XmlFileHandler;
import lombok.extern.slf4j.Slf4j;
import model.read.ConfiguringClass;
import model.generate.GenerationClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by Hack
 * Date: 17-Apr-20 08:17
 */
@Slf4j
public class XmlTest {
    private ConfigAPI api;

    @Before
    public void init() {
        api = new ConfigAPI();
        api.registerFileHandler(XmlFileHandler.getInstance());
    }

    @Test
    public void loadFile() {
        log.info("loadFile test start");
        api.addConfigurablePackage("model.read");
        api.addFilePath("src/test/resources/read");
        api.load();
        Assert.assertEquals(3, (int) ConfiguringClass.intVal);
        Assert.assertTrue(ConfiguringClass.boolVal);
        Assert.assertEquals(ConfiguringClass.intList.get(2), (Integer) 3);
        Assert.assertEquals(ConfiguringClass.getPrivateDouble(), (Double) 2.147);
        Assert.assertEquals(ConfiguringClass.mapStrDbl.get("c"), (Double) 3.0);
        Assert.assertTrue(ConfiguringClass.intSet.contains(111));
        Assert.assertEquals(ConfiguringClass.rawInt, 123);
        Assert.assertEquals(ConfiguringClass.mapStringListInt.get("a").get(2), (Integer) 3);
        log.info("loadFile test end");
    }

    @Test
    public void generateFile() {
        log.info("generateFile test start");
        api.addConfigurablePackage("model.generate");
        api.addFilePath("src/test/resources/generate");
        api.load();
        GenerationClass.rawInt = 10;
        GenerationClass.strIntMap.clear();
        GenerationClass.intVal = -10;
        api.load();
        Assert.assertEquals(GenerationClass.rawInt, 3);
        Assert.assertEquals(GenerationClass.strIntMap.size(), 3);
        Assert.assertEquals(GenerationClass.intVal, (Integer) 0);
        new File("src/test/resources/generate/generation.xml").delete();
        log.info("generateFile test end");
    }
}
