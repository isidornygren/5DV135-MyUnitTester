package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TesterModelTest {
    TesterModel testerModel;
    @Before
    public void setUp() throws Exception {
        testerModel = new TesterModel("Model.Test");
    }

    @After
    public void tearDown() throws Exception {
        testerModel = null;
    }

    @Test
    public void getMethods() throws Exception {
        ArrayList<Method> methods = testerModel.getMethods();
        assertEquals(methods.size(), 7);
        for(Method method : methods){
            assertTrue(method.getName().startsWith("test"));
            assertEquals(method.getParameterCount(), 0);
        }
    }

    @Test
    public void run() throws Exception {
        ArrayList<Method> methods = testerModel.getMethods();
        for(Method method : methods){
            ResultObject result = testerModel.run(method);
            assertNotEquals(result, null);
        }
    }

    @Test
    public void runAll() throws Exception {
    }

    @Test
    public void formatFormattingErrors() throws Exception {
    }

    @Test
    public void formatResults() throws Exception {
    }

}