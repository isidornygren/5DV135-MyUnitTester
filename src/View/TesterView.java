package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Builds the main window for the application using Swing.
 * @version 1.0
 * @author Isidor Nygren
 */
public class TesterView{
    private final JFrame frame;

    private JTextArea textArea;
    private JTextField textField;
    private JButton runButton;

    public TesterView(String title){
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 300));

        frame.add(buildOutputPanel(), BorderLayout.CENTER); // Needs to be first to build the output text-area
        frame.add(buildTopPanel(), BorderLayout.PAGE_START);
        frame.add(buildBottomPanel(), BorderLayout.PAGE_END);

        frame.pack();
    }

    /**
     * Sets the visibility of the window
     */
    public void setVisible(boolean visible){
        this.frame.setVisible(visible);
    }

    /**
     * Builds a panel with the text input box and a button to run the tests
     * @return the created panel
     */
    private JPanel buildTopPanel(){
        JPanel panel = new JPanel(new FlowLayout());
        textField = new JTextField(20);
        runButton = new JButton("Run tests");

        panel.add(textField);
        panel.add(runButton);

        return panel;
    }

    /**
     * Builds a panel consisting of scrollable text area
     * @return the created panel
     */
    private JPanel buildOutputPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        textArea = new JTextArea(20,50);
        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        panel.add(scrollPane);

        return panel;
    }

    /**
     * Builds a panel with a clear button
     * @return the created panel
     */
    private JPanel buildBottomPanel(){
        JPanel panel = new JPanel(new FlowLayout());
        JButton clearButton = new JButton("Clear");

        clearButton.addActionListener(e -> clearText());
        panel.add(clearButton);

        return panel;
    }

    /**
     * Adds a external listener to the test button
     * @param listener The ActionEvent listener that runs when the button is pressed
     */
    public void addTestListener(ActionListener listener){
        runButton.addActionListener(listener);
    }

    /**
     * Clears all the text in the default text area for the view
     */
    public void clearText(){
        this.textArea.setText(null);
    }

    /**
     * Gets the current String from the textField input box
     * @return the String from the input
     */
    public String getInput(){
        return this.textField.getText();
    }

    /**
     * Appends the text to the default text area in the view
     * @param text the String to print out
     */
    public void print(String text){
        this.textArea.append(text);
    }

    /**
     * creates a new dialog box for error messages
     * @param title The title to appear at the top bar of the window
     * @param message The message to be displayed inside the error dialog
     */
    public void errorMessage(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

}