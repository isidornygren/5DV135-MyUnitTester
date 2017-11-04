import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Creates a listener connected to Button.
 * The button is connected to a JTextField and a JTextArea, reading the content
 * of the JTextField on press and runs a series of tests that outputs on the JTextArea.
 * @version 1.0
 * @author Isidor Nygren
 */

class RunButtonListener implements ActionListener {

    private final JTextArea textArea;
    private final JTextField textField;

    /**
     * Constructor for a button listener that given user input about a test,
     * tries to run that test and outputs the result of that test to a textarea.
     * @param textArea a textarea to output text to
     * @param textField a textfield to gather user input from
     */
    RunButtonListener(JTextArea textArea, JTextField textField){
        this.textArea = textArea;
        this.textField = textField;
    }

    /**
     * Method called when the run button is pressed, prints any formatting errors
     * found in the class file and then runs the test.
     * @param event ActionEvent gives information about the event and its source
     */
    public void actionPerformed(ActionEvent event){
        String text = this.textField.getText();
        try {
            Tester tester = new Tester(text);

            //Clear the textarea
            this.textArea.setText(null);

            ArrayList<String> formattingErrors = tester.setUp();
            this.textArea.append("Running " + text + ":\n\n");

            printFormattingErrors(formattingErrors);

            ArrayList<ResultObject> results = tester.run();
            printResults(results);

        }catch(ClassNotFoundException e){
            new ErrorMessage("Error", "Error: Class " + text + " not found.\n" + e + "\n");
        }catch(ClassFormatException e){
            new ErrorMessage("Error", "Error: Wrong class format.\n" + e + "\n");
        }catch(NoSuchMethodException e){
            new ErrorMessage("Error", "Could not find constructor for test\n" + e);
        }catch(InstantiationException e){
            new ErrorMessage("Error", "Could not create new instance of test class.\n" +
                "Is the Class constructor correctly formatted?\n" + e);
        }catch(IllegalAccessException e){
            new ErrorMessage("Error", "No access to class file\n" + e);
        }
    }

    /**
     * Prints all the formatting errors found during the parsing of the given class
     * @param formattingErrors a list of all the formatting errors as Strings
     */
    private void printFormattingErrors(ArrayList<String> formattingErrors){
        if (formattingErrors.size() > 0){
            this.textArea.append("Found " + formattingErrors.size() + " formatting errors in test class:\n");
            for(String error : formattingErrors){
                this.textArea.append(error + "\n");
            }
            this.textArea.append("\n");
        }
    }

    /**
     * Prints all the results from an array of resultObjects, prints a counter at
     * the end of the printout.
     * @param results an array of ResultObjects
     */
    private void printResults(ArrayList<ResultObject> results){
        /* Counter variables */
        Integer success = 0;
        Integer failed = 0;
        Integer exception = 0;
        long elapsedTime = 0;

        /* Count all the results for the methods */
        for(ResultObject result : results){
            this.textArea.append(result.getMethod().getName() + ": ");
            elapsedTime += result.getTime();
            if(result.isException()){
                exception++;
                this.textArea.append("FAILED Generated a " + result.getException() + "\n");
            }else if(result.isSuccess()){
                success++;
                this.textArea.append("SUCCESS\n");
            }else{
                failed++;
                this.textArea.append("FAILED\n");
            }
        }
        /* Print the results to the given textArea object */
        Integer total = success + failed + exception;
        String output = String.format("%d tests total, %d successes, %d fails, %d fails because of exceptions\n",
                total, success, failed, exception);

        if(elapsedTime > 0){
            String time = String.format("\nFinished tests in %fs, %.2f tests/s.\n\n", elapsedTime*Math.pow(10,-9), total/(elapsedTime*Math.pow(10,-9)));
            this.textArea.append(time);
        }
        this.textArea.append(output);
    }
}
