import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import javax.swing.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Checks for classes that inherites the TestClass Interface and runs the
 * methods of that class.
 * @version 1.0
 * @author Isidor Nygren
 */

class Tester {
    private Class<?> classInstance;
    private JTextArea textArea;
    private Object instance;

    /**
     * @param name The name of the class to be tested.
     * @param textArea A textarea to output the results of the tests to.
     * @throws ClassNotFoundException If the class is not found in this package.
     * @throws ClassFormatException If the .class file contains errors.
     * @throws ClassFormatError if the given class is not following the given spec.
     */
    Tester(String name, JTextArea textArea) throws InstantiationException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, ClassFormatException, ClassFormatError{
        this.textArea = textArea;
        classInstance = Class.forName(name);
        if (classInstance.isInterface()) {
            throw new ClassFormatError();
        } else {
            instance = classInstance.newInstance();
            if(instance instanceof TestClass){
                if(classInstance.getConstructor().getParameterCount() != 0){
                    /* Class constructor arguments is not legal as defined in the spec */
                    throw new ClassFormatError();
                }
            }else{
                throw new ClassNotFoundException();
            }
        }
        /* All other exceptions will be internal errors opening files */
        /* and should not be prompted via the input. */

    }

    /**
     * Runs through the tests in the given class.
     * The tests are defined as methods beginning with "test".
     * If a setUp method is found, it runs that method before every test.
     * If a tearDown method is found, it runs that method after every test.
     */
    void run(){
        List<Method> tests = new ArrayList<>(); // Array of all the names of the test methods
        Method setUp = null;
        Method tearDown = null;

        /* Get all methods from class */
        Method[] testMethods = classInstance.getDeclaredMethods();
        for(Method method : testMethods){
            if(method.getParameterCount() == 0) {   // Don't add tests with arguments
                if (method.getName().startsWith("test")) {
                    tests.add(method);
                } else if (method.getName().equals("setUp")) {
                    setUp = method;
                } else if (method.getName().equals("tearDown")) {
                    tearDown = method;
                }
            }
        }
        /* Counters */
        Integer success = 0;
        Integer failed = 0;
        Integer exceptionFailed = 0;

        /* Run all methods */
        for(Method test : tests){
            try{
                if(setUp != null){
                    setUp.invoke(instance);
                }
                this.textArea.append(test.getName() + ": ");
                Object value = test.invoke(instance);
                if((boolean)value){
                    success++;
                    this.textArea.append("SUCCESS\n");
                }else{
                    failed++;
                    this.textArea.append("FAILED\n");
                }
                if(tearDown != null){
                    tearDown.invoke(instance);
                }
            }catch (Exception e){
                exceptionFailed++;
                this.textArea.append("FAILED Generated a " + e + "\n");
            }
        }
        /* Print the results to the given textArea object */
        Integer total = success + failed + exceptionFailed;
        this.textArea.append("\n" + success + "/" + total + " tests succeded" + "\n");
        this.textArea.append(failed + "/" + total + " tests failed" + "\n");
        this.textArea.append(exceptionFailed + "/" + total + " failed because of an exception." + "\n\n");
    }
}
