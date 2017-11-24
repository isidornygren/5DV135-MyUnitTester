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
    }

    @Test
    public void getClassErrors() throws Exception {
        assertEquals(testerModel.getClassErrors().size(),0);
    }

    @Test
    public void getMethodsOnlyTests() throws Exception {
        ArrayList<Method> methods = testerModel.getMethods();
        for(Method method : methods){
            assertTrue(method.getName().startsWith("test"));
        }
    }

    @Test
    public void getMethodsNoParameters() throws Exception {
        ArrayList<Method> methods = testerModel.getMethods();
        for(Method method : methods){
            assertEquals(method.getParameterCount(), 0);
        }
    }

    @Test
    public void run() throws Exception {
        ArrayList<Method> methods = testerModel.getMethods();
        for(Method method : methods){
            ResultObject result = testerModel.run(method);
            assertNotNull(result);
        }
    }

    @Test
    public void runAll() throws Exception {
        ArrayList<ResultObject> results = testerModel.runAll();
        for(ResultObject result : results){
            assertNotNull(result);
        }
    }

    @Test
    public void formatFormattingErrors() throws Exception {
        assertTrue(testerModel.formatFormattingErrors().startsWith("Found 1 formatting errors in test class"));
    }

    @Test
    public void formatResults() throws Exception {
        testerModel.runAll();
        assertTrue(testerModel.formatResults().startsWith("\nFinished tests in "));
    }
}