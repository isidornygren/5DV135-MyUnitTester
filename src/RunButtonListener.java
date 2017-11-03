import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            Tester tester = new Tester(text, this.textArea);
            tester.run();
        }catch(ClassNotFoundException e){
            new ErrorMessage("Error", "Error: Class " + text + " not found.\n" + e + "\n");
        }catch(ClassFormatException e){
            new ErrorMessage("Error", "Error: Wrong class format.\n" + e + "\n");
        }
    }
}
