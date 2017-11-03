package assignment_1;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the main window for the application using Swing.
 * @version 1.0
 * @author Isidor Nygren
 */

class TestWindow {
    private JFrame frame;

    private JTextArea textArea;
    private JTextField textField;

    TestWindow(String title){
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(buildOutputPanel(), BorderLayout.CENTER); // Needs to be first to build the ouput text-area
        frame.add(buildTopPanel(), BorderLayout.PAGE_START);
        frame.add(buildBottomPanel(), BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Builds a panel with the text input box and a button to run the tests
     * @return the created panel
     */
    private JPanel buildTopPanel(){
        JPanel panel = new JPanel(new FlowLayout());
        textField = new JTextField(20);
        JButton runButton = new JButton("Run tests");

        runButton.addActionListener(new RunButtonListener(textArea, textField));
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

        clearButton.addActionListener(new ClearButtonListener(textArea));
        panel.add(clearButton);

        return panel;
    }

}
