package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TesterModelTest {
    private TesterModel testerModel;
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
            assertNotNull(result.getMethod());
            assertNotNull(result.getTime());
            assertNotNull(result.isSuccess());
        }
    }

    @Test
    public void runAll() throws Exception {
        ArrayList<ResultObject> results = testerModel.runAll();
        for(ResultObject result : results){
            assertNotNull(result);
            assertNotNull(result.getMethod());
            assertNotNull(result.getTime());
            assertNotNull(result.isSuccess());
        }
    }

    @Test
    public void formatFormattingErrors() throws Exception {
        assertNotNull(testerModel.formatFormattingErrors());
        assertTrue(testerModel.formatFormattingErrors().startsWith("Found 1 formatting errors in test class"));
    }

    @Test
    public void formatResults() throws Exception {
        testerModel.runAll();
        assertNotNull(testerModel.formatResults());
        assertTrue(testerModel.formatResults().startsWith("\nFinished tests in "));
    }
}