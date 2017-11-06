package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ResultObjectTest {
    private ResultObject resultObject;
    Class<?> classInstance;
    Method method;
    double time;

    public boolean testMethod(){
        return true;
    }
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
    public void formatTime() throws Exception{
        assertEquals("1.000s", ResultObject.formatTime(1000000000));
        assertEquals("0.100s", ResultObject.formatTime(100000000));
        assertEquals("1.000ms", ResultObject.formatTime(1000000));
    }

    @Test
    public void formatString() throws Exception {
        String formatString = resultObject.formatString();
        String testString = String.format("%s: SUCCESS. Finished in %s\n", resultObject.getMethod().getName(), ResultObject.formatTime(resultObject.getTime()));
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
         ResultObject exceptionResult = new ResultObject(method, false, time, new IllegalAccessException());
         assertEquals(exceptionResult.getException().getMessage(), new IllegalAccessException().getMessage());
    }

    @Test
    public void getTime() throws Exception {
        assertTrue(resultObject.getTime() == time);
    }

}