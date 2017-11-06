import Model.TestClass;

/**
 * A basic test class that checks that tests does not run without a return value
 * @version 1.0
 * @author Isidor Nygren
 */

public class Test5 implements TestClass {

    public Test5(){

    }

    public void testReturn1(){
        throw new ClassCastException();
    }
    public String testReturn2(){
        throw new ClassCastException();
    }
    public Integer testReturn3(){
        throw new ClassCastException();
    }
}