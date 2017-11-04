import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a listener for a Button.
 * Clears all the text at a supplied textArea when the button is pressed.
 * @version 1.0
 * @author Isidor Nygren
 */

public class ClearButtonListener implements ActionListener {

    private final JTextArea textArea;

    /**
     *
     * @param textArea
     */
    ClearButtonListener(JTextArea textArea){
        this.textArea = textArea;
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e){
        this.textArea.setText(null);
    }
}
