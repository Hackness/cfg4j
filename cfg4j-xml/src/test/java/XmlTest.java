import com.hackness.cfg4j.core.ConfigAPI;
import com.hackness.cfg4j.xml.XmlFileHandler;
import model.ConfiguringClass;
import model.generation.GenerationClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by Hack
 * Date: 17-Apr-20 08:17
 */
public class XmlTest {
    @Test
    public void loadFile() {
        ConfigAPI api = ConfigAPI.getInstance();
        api.registerFileHandler(XmlFileHandler.getInstance());
        api.addConfigurablePackage("model");
        api.addFilePath("src/test/resources");
        api.load();
        Assert.assertEquals(3, (int) ConfiguringClass.intVal);
        Assert.assertTrue(ConfiguringClass.boolVal);
        Assert.assertEquals(ConfiguringClass.intList.get(2), (Integer) 3);
        Assert.assertEquals(ConfiguringClass.getPrivateDouble(), (Double) 2.147);
        Assert.assertEquals(ConfiguringClass.mapStrDbl.get("c"), (Double) 3.0);
        Assert.assertTrue(ConfiguringClass.intSet.contains(111));
        Assert.assertEquals(ConfiguringClass.rawInt, 123);
        Assert.assertEquals(ConfiguringClass.mapStringListInt.get("a").get(2), (Integer) 3);
    }

    //TODO: package-url transform is probably wrong. At least wrong for the test scope
    @Test
    public void generateFile() {
        ConfigAPI api = ConfigAPI.getInstance();
        api.registerFileHandler(XmlFileHandler.getInstance());
        api.addConfigurablePackage("model.generation");
        api.addFilePath("src/test/resources");
        api.load();
        GenerationClass.rawInt = 10;
        GenerationClass.strIntMap.clear();
        GenerationClass.intVal = -10;
        api.load();
        Assert.assertEquals(GenerationClass.rawInt, 3);
        Assert.assertEquals(GenerationClass.strIntMap.size(), 3);
        Assert.assertEquals(GenerationClass.intVal, (Integer) 0);
        new File("src/test/resources/generation.xml").delete();
    }
}
