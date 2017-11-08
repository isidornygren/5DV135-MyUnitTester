package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ResultObjectTest {
    private ResultObject resultObject;
    private Class<?> classInstance;
    private Method method;
    private double time;

    @Before
    public void setUp() throws Exception {
        classInstance = Class.forName("Model.Test");
        method = classInstance.getMethod("testIntegerToString");
        time = Math.random();
        resultObject = new ResultObject(method, true, time);
    }

    @After
    public void tearDown() throws Exception {
        resultObject = null;
    }

    @Test
    public void formatString() throws Exception {
        String formatString = resultObject.formatString();
        String testString = String.format("%s: SUCCESS. Finished in %s\n", resultObject.getMethod().getName(), TimeFormat.seconds(resultObject.getTime()));
        assertEquals(formatString, testString);
    }

    @Test
    public void getMethod() throws Exception {
        assertTrue(method.equals(resultObject.getMethod()));
    }

    @Test
    public void isSuccess() throws Exception {
        assertTrue(resultObject.isSuccess());
    }

    @Test
    public void isException() throws Exception {
        assertFalse(resultObject.isException());
        ResultObject exceptionResult = new ResultObject(method, false, time, new IllegalAccessException());
        assertTrue(exceptionResult.isException());
    }

    @Test
    public void getException() throws Exception {
        assertNull(resultObject.getException());
        ResultObject exceptionResult = new ResultObject(method, false, time, new IllegalAccessException());
        assertEquals(exceptionResult.getException().getMessage(), new IllegalAccessException().getMessage());
    }

    @Test
    public void getTime() throws Exception {
        assertTrue(resultObject.getTime() == this.time);
    }

}