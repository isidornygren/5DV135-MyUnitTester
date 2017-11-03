package _5dv135.assignment1;

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

    public RunButtonListener(JTextArea textArea, JTextField textField){
        this.textArea = textArea;
        this.textField = textField;
    }
    public void actionPerformed(ActionEvent event){
        String text = this.textField.getText();
        try {
            Tester tester = new Tester(text, this.textArea);
            tester.run();
        }catch(ClassNotFoundException e){
            this.textArea.append("Error: Class not found.\n\t" + e + "\n");
        }catch(ClassFormatException e){
            this.textArea.append("Error: Wrong class format.\n\t" + e + "\n");
        }
    }
}
