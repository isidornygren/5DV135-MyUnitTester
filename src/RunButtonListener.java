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

    RunButtonListener(JTextArea textArea, JTextField textField){
        this.textArea = textArea;
        this.textField = textField;
    }
    /* Try to run a test for the given filename in the textfield  */
    public void actionPerformed(ActionEvent event){
        String text = this.textField.getText();
        try {
            Tester tester = new Tester(text);
            ArrayList<String> formattingErrors = tester.setUp();
            this.textArea.append("Running " + text + ".\n\n");

            if (formattingErrors.size() > 0){
                this.textArea.append("Found " + formattingErrors.size() + " formatting errors in test class.\n");
                for(String error : formattingErrors){
                    this.textArea.append("\t" + error + "\n");
                }
            }
            Integer success = 0;
            Integer failed = 0;
            Integer exception = 0;

            ArrayList<ResultObject> results = tester.run();
            /* Count all the results for the methods */
            for(ResultObject result : results){
                this.textArea.append(result.getMethod().getName() + ": ");
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
            this.textArea.append("\n" + success + "/" + total + " tests succeded" + "\n");
            this.textArea.append(failed + "/" + total + " tests failed" + "\n");
            this.textArea.append(exception + "/" + total + " failed because of an exception." + "\n\n");

        }catch(ClassNotFoundException e){
            new ErrorMessage("Error", "Error: Class " + text + " not found.\n" + e + "\n");
        }catch(ClassFormatException e){
            new ErrorMessage("Error", "Error: Wrong class format.\n" + e + "\n");
        }catch(NoSuchMethodException e){
            new ErrorMessage("Error", "Could not find constructor for test\n" + e);
        }catch(InstantiationException e){
            new ErrorMessage("Error", "Could not create new instance of test class.\n" +
                "Does the test class follow the conventions?\n" + e);
        }catch(IllegalAccessException e){
            new ErrorMessage("Error", "No access to class file\n" + e);
        }
    }
}
