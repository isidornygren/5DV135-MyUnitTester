package Model;

import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @version 1.0
 * @author Isidor Nygren
 */

public class TesterModel {
    private Class<?> classInstance;
    private Object instance;

    private ArrayList<Method> tests = new ArrayList<>(); // Array of all the test methods in the class
    private ArrayList<ResultObject> results = new ArrayList<>(); // Array of all the test results after run
    private ArrayList<String> formattingErrors = new ArrayList<>(); // Array of formatting errors of class

    private Method setUp = null;
    private Method tearDown = null;

    /**
     * A tester object that tries to open a Class by its name using the reflection library,
     * if a class is found, checks so it follows a set of rules:
     * - Class has to be implementing the TestClass class
     * - The constructor should take zero parameters
     * @param name The name of the class to be tested.
     * @throws ClassNotFoundException If the class is not found in this package.
     * @throws ClassFormatException If the .class file contains errors.
     * @throws ClassFormatError if the given class is not following the given spec.
     * @throws InstantiationException if the given class constructor gives error.
     * @throws IllegalAccessException if reading the class file produces an error.
     * @throws NoSuchMethodException if there exists no constructor for the class.
     */
    public TesterModel(String name) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException, ClassFormatException, ClassFormatError{
        classInstance = Class.forName(name);
        if (classInstance.isInterface()) {
            throw new ClassFormatError();
        } else {
            instance = classInstance.newInstance();
            if(instance instanceof TestClass){
                if(classInstance.getConstructor().getParameterCount() != 0){
                    /* Class constructor arguments is not legal as defined in the spec */
                    throw new ClassFormatError();
                }else{
                    setUp();
                }
            }else{
                throw new ClassNotFoundException();
            }
        }
    }

    /**
     * Sets the Model.Tester object up for the tests in the method by finding all the declared methods
     * through the reflection library, finds all methods beginning with "test" and checks that they are
     * "legal" e.g. following the following rules:
     * - The method should take zero parameters
     * - The method name needs to start with "test"
     * - The method needs to return a boolean
     * If the methods are legal they get added to the return array.
     * If the method setUp and tearDown is found these are added to the Model.Tester object to be run before and
     * after every test.
     */
    private void setUp(){
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
    }

    /**
     * Returns a list of all the available methods from the class
     * This should be run after a setUp() has run to ensure all classes have been added
     * @return the list of legal methods in the class
     */
    public ArrayList<Method> getMethods(){
        return this.tests;
    }
    /**
     * Runs through a singular test in the given class.
     * If a setUp method was found, it runs that method before every test.
     * If a tearDown method was found, it runs that method after every test.
     * @return A resultObject that includes the success and any errors that occurred during the test
     */
    public ResultObject run(Method method){
        long startTime = System.nanoTime();
        ResultObject result;
        try{
            if(setUp != null){
                setUp.invoke(instance);
            }
            result = new ResultObject(method, (boolean)method.invoke(instance), System.nanoTime() - startTime);
            if(tearDown != null){
                tearDown.invoke(instance);
            }
        }catch (Exception e){
            result = new ResultObject(method, false, System.nanoTime() - startTime, e);
        }
        results.add(result);
        return result;
    }
    /**
     * Runs through the tests in the given class.
     * The tests are defined as methods beginning with "test".
     * If a setUp method was found, it runs that method before every test.
     * If a tearDown method was found, it runs that method after every test.
     * @return A resultObject that includes the success and any errors that occurred during the test
     */
    public ArrayList<ResultObject> runAll(){
        for(Method test : tests){
            run(test);
        }
        return results;
    }
    /**
     * Prints all the formatting errors found during the parsing of the given class
     * @return a formatted list of errors in one string
     */
    public String formatFormattingErrors(){
        StringBuilder formattedString = new StringBuilder();

        if (formattingErrors.size() > 0){
            formattedString.append("Found " + formattingErrors.size() + " formatting errors in test class:\n");
            for(String error : formattingErrors){
                formattedString.append(error + "\n");
            }
            formattedString.append("\n");
        }

        return formattedString.toString();
    }
    /**
     * Formats all the results from an array of resultObjects, prints a counter at
     * the end of the printout.
     * @return the formatted results
     */
    public String formatResults(){
        /* Counter variables */
        Integer success = 0;
        Integer failed = 0;
        Integer exception = 0;
        long elapsedTime = 0;
        StringBuilder formattedString = new StringBuilder();

        /* Count all the results for the methods */
        for(ResultObject result : results){
            elapsedTime += result.getTime();
            if(result.isException()){
                exception++;
            }else if(result.isSuccess()){
                success++;
            }else{
                failed++;
            }
        }
        Integer total = success + failed + exception;
        if(elapsedTime > 0){
            String time = String.format("\nFinished tests in %s, %.2f tests/s.\n\n",
                    ResultObject.formatTime(elapsedTime), total/(elapsedTime*Math.pow(10,-9)));
            formattedString.append(time);
        }
        String output = String.format("%d tests total, %d successes, %d fails, %d fails because of exceptions\n",
                total, success, failed, exception);
        formattedString.append(output);
        return formattedString.toString();
    }
}
