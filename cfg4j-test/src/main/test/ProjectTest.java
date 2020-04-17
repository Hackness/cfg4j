import com.hackness.cfg4j.core.ConfigAPI;
import com.hackness.cfg4j.xml.XmlFileHandler;
import org.junit.Test;

/**
 * Created by Hack
 * Date: 17-Apr-20 03:04
 */
public class ProjectTest {
    @Test
    public void xmlStartUpTest() {
        ConfigAPI.getInstance().registerFileHandler(XmlFileHandler.getInstance());
        ConfigAPI.getInstance().init();
    }
}
