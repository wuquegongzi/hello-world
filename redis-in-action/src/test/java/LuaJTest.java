import java.net.URISyntaxException;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * lua -java test
 *
 * @author ml.c
 * @date 3:34 PM 5/6/21
 **/
public class LuaJTest {

    Logger logger = Logger.getGlobal();

    @Test
    public void test() throws URISyntaxException {
        String luaFileName = getClass().getClassLoader().getResource("lua/hello.lua").toURI().getPath();
        Globals globals = JsePlatform.standardGlobals();
        LuaValue transcoderObj = globals.loadfile(luaFileName).call();
        LuaValue func = transcoderObj.get(LuaValue.valueOf("hello"));
        String result = func.call().toString();
        logger.info("result---"+result);
    }
}
