import com.hackness.cfg4j.core.ConfigAPI;
import com.hackness.cfg4j.xml.XmlFileHandler;
import model.ConfiguringClass;
import org.junit.Assert;
import org.junit.Test;

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
        api.addFilePath("src/test/resources/test.xml");
        api.init();
        Assert.assertEquals(3, (int) ConfiguringClass.intVal);
        Assert.assertTrue(ConfiguringClass.boolVal);
        Assert.assertEquals(ConfiguringClass.intList.get(2), (Integer) 3);
        Assert.assertEquals(ConfiguringClass.getPrivateDouble(), (Double) 2.147);
        Assert.assertEquals(ConfiguringClass.mapStrDbl.get("c"), (Double) 3.0);
        Assert.assertTrue(ConfiguringClass.intSet.contains(111));
        Assert.assertEquals(ConfiguringClass.rawInt, 123);
        Assert.assertEquals(ConfiguringClass.mapStringListInt.get("a").get(2), (Integer) 3);
    }
}
