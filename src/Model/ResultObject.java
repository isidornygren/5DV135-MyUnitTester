package Model;

import java.lang.reflect.*;

/**
 * Includes the the result and the errors from a test
 * @version 1.0
 * @author Isidor Nygren
 */
public class ResultObject {
    private Method method = null;
    private boolean success = false;
    private double time = 0;
    private Exception exception = null;  // If the test generated an exception

    /**
     * @param method the method that was tested
     * @param success if the test was a success = if the method returned true
     */
    public ResultObject(Method method, boolean success, double time){
        this.method = method;
        this.success = success;
        this.time = time;
    }

    /**
     * Secondary constructor for the object that is used if the method generated
     * and exception during run.
     * @param method the method that was tested
     * @param success if the test was a success = if the method returned true
     * @param e the generated exception
     */
    public ResultObject(Method method, boolean success, double time, Exception e){
        this.method = method;
        this.success = success;
        this.time = time;
        this.exception = e;
    }
    /**
     * Prints the result of one test
     * @return a string formatted for easy readability
     */
    public String formatString(){
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(getMethod().getName()).append(": ");
        if(isException()){
            formattedString.append("FAILED Generated a ").append(getException());
        }else if(isSuccess()){
            formattedString.append("SUCCESS");
        }else{
            formattedString.append("FAILED");
        }
        return String.format("%s. Finished in %s\n", formattedString.toString(), ResultObject.formatTime(getTime()));
    }

    /**
     * Getter for the method that the result is based on
     * @return the method
     */
    public Method getMethod(){
        return this.method;
    }

    /**
     * Returns if the result was a success
     * @return true if it was a success, otherwise false
     */
    public Boolean isSuccess(){
        return this.success;
    }

    /**
     * Returns if there occurred an exception during the run of the test
     * @return true if there occurred an exception, otherwise false
     */
    public Boolean isException(){
        return (this.exception == null) ? false : true;
    }

    /**
     * Returns the exception that occured
     * @return the exception if it occured, otherwise null
     */
    public Exception getException(){
        return this.exception;
    }

    /**
     * Returns the time it took for the test to run
     * @return the time in ms
     */
    public double getTime(){
        return this.time;
    }

    /**
     * Formats a small double into a human-readable format
     * @param time the time in nanoseconds to format
     * @return a string formatted in x.xxxs format.
     */
    public static String formatTime(double time) {
        String string;
        if (time > Math.pow(10, 9) * 60) { // Minutes
            string = String.format("%.3fmin", time/(Math.pow(10, 9) * 60));
        } else if (time > Math.pow(10, 7)) { // Seconds
            string = String.format("%.3fs", time/(Math.pow(10, 9)));
        } else if (time > Math.pow(10, 4)) { // Milliseconds
            string = String.format("%.3fms", time/(Math.pow(10, 6)));
        } else if (time > 10) { // Microseconds
            string = String.format("%.3fµs", time/(Math.pow(10, 3)));
        } else{ // Nanosecond
            string = String.format("%.3fns", time);
        }
        return string;
    }
}
