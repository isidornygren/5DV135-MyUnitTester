import java.lang.reflect.*;

/**
 * Includes the the result and the errors from a test
 * @version 1.0
 * @author Isidor Nygren
 */
public class ResultObject {
    private Method method = null;
    private boolean success = false;
    private Exception exception = null;  // If the test generated an exception

    /**
     * @param method the method that was tested
     * @param success if the test was a success = if the method returned true
     */
    ResultObject(Method method, boolean success){
        this.method = method;
        this.success = success;
    }

    /**
     * Secondary constructor for the object that is used if the method generated
     * and exception during run.
     * @param method the method that was tested
     * @param success if the test was a success = if the method returned true
     * @param e the generated exception
     */
    ResultObject(Method method, boolean success, Exception e){
        this.method = method;
        this.success = success;
        this.exception = e;
    }

    /**
     * Getter for the method that the result is based on
     * @return the method
     */
    Method getMethod(){
        return this.method;
    }

    /**
     * Returns if the result was a success
     * @return true if it was a success, otherwise false
     */
    Boolean isSuccess(){
        return this.success;
    }

    /**
     * Returns if there occurred an exception during the run of the test
     * @return true if there occurred an exception, otherwise false
     */
    Boolean isException(){
        return (this.exception == null) ? false : true;
    }

    /**
     * Returns the exception that occured
     * @return the exception if it occured, otherwise null
     */
    Exception getException(){
        return this.exception;
    }
}
