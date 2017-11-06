import Model.TestClass;

/**
 * A basic test class that checks that tests run without setup and teardown methods
 * @version 1.0
 * @author Isidor Nygren
 */

public class Test4 implements TestClass {
    public Test4(){

    }

    // Always returns true
    public boolean testTrue(){
        return true;
    }

    // Always returns true
    public boolean testLag() {
        Integer i = 0;
        while (i < 1000000000){
            i++;
        }
        return true;
    }

    // Always returns false
    public boolean testFalse(){
        return false;
    }
}