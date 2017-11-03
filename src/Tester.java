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
    //private JTextArea textArea;
    private Object instance;

    private List<Method> tests = new ArrayList<>(); // Array of all the test methods in the class
    private Method setUp = null;
    private Method tearDown = null;

    /**
     * @param name The name of the class to be tested.
     * @throws ClassNotFoundException If the class is not found in this package.
     * @throws ClassFormatException If the .class file contains errors.
     * @throws ClassFormatError if the given class is not following the given spec.
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    Tester(String name) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException, ClassFormatException, ClassFormatError{
        //this.textArea = textArea;
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
    }

    /**
     * Sets the Tester object up for the tests in the method by finding all the declared methods
     * through the reflection library, finds all methods beginning with "test" and checks that they are
     * legal, if they are legal they get added to the return array.
     * If the method setUp and tearDown is found these are added to the Tester object to be run before and
     * after every test.
     * @return The array consisting of every "test"-method
     */
    ArrayList<String> setUp(){
        ArrayList<String> formattingErrors = new ArrayList<>();

        /* Get all methods from class */
        Method[] testMethods = classInstance.getDeclaredMethods();
        for(Method method : testMethods){
            if(method.getParameterCount() == 0) {   // Don't add tests with arguments
                if (method.getName().startsWith("test")) {
                    if(method.getReturnType().equals(Boolean.TYPE)){
                        this.tests.add(method);
                    }else{
                        formattingErrors.add(method.getName() + " does not return a boolean.");
                    }
                } else if (method.getName().equals("setUp")) {
                    this.setUp = method;
                } else if (method.getName().equals("tearDown")) {
                    this.tearDown = method;
                }
            }else{
                formattingErrors.add(method.getName() + " has parameters.");
            }
        }
        return formattingErrors;
    }
    /**
     * Runs through the tests in the given class.
     * The tests are defined as methods beginning with "test".
     * If a setUp method was found, it runs that method before every test.
     * If a tearDown method was found, it runs that method after every test.
     * @return A resultObject that includes the success and any errors that occurred during the test
     */
    ArrayList<ResultObject> run(){
        ArrayList<ResultObject> results = new ArrayList<>();
        for(Method test : tests){
            try{
                if(setUp != null){
                    setUp.invoke(instance);
                }
                if((boolean)test.invoke(instance)){

                    results.add(new ResultObject(test, true));
                }else{
                    results.add(new ResultObject(test, false));
                }
                if(tearDown != null){
                    tearDown.invoke(instance);
                }
            }catch (Exception e){
                results.add(new ResultObject(test, false, e));
            }
        }
        return results;
    }
}
