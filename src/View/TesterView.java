package View;

import Controller.TesterController;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the main window for the application using Swing.
 * @version 1.0
 * @author Isidor Nygren
 */
public class TesterView {
    private JFrame frame;

    private JTextArea textArea;
    private JTextField textField;
    private TesterController controller;

    public TesterView(TesterController controller, String title){
        this.controller = controller;
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        JButton runButton = new JButton("Run tests");

        runButton.addActionListener(e -> controller.runTest(e));

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