import javax.swing.*;

/**
 * Creates a new dialog box for error messages
 * @version 1.0
 * @author Isidor Nygren
 */
public class ErrorMessage {
    /**
     * @param title The title to appear at the top bar of the window
     * @param message The message to be displayed inside the error dialog
     */
    ErrorMessage(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
