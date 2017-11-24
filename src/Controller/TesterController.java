package Controller;
import Model.ResultObject;
import Model.TesterModel;
import View.TesterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for building and handling the model and the view
 * @version 1.0
 * @author Isidor Nygren
 */
public class TesterController{
    private TesterView view;
    private TestWorker worker;

    /**
     * Builds a new view object based on swing
     */
    public TesterController(){
        javax.swing.SwingUtilities.invokeLater(() -> {
            view = new TesterView("MyUnitTester");
            // Add the test button listener
            view.addTestListener(this::runTest);
            view.setVisible(true);
        });
    }

    /**
     * Method called when the run button is pressed, prints any formatting errors
     * found in the class file and then runs the test.
     * @param event ActionEvent gives information about the event and its source
     */
    public void runTest(ActionEvent event){
        String className = this.view.getInput();

        /* Check so no worker is running in the background already */
        if(this.worker != null){
            this.worker.cancel(true);
        }

        TesterModel test = new TesterModel(className);
        ArrayList<String> errors = test.getClassErrors();
        if(errors.size() > 0){
            // Print the first error if an error was encountered
            this.view.errorMessage("Error", errors.get(0));
        }else{
            this.view.clearText();
            this.view.print("Running " + className + ":\n\n");
            this.view.print(test.formatFormattingErrors());

            /* Run the tests for the class in a separate thread */
            this.worker = new TestWorker(test, this.view);
            this.worker.execute();
        }
    }
}

/**
 * A worker extending the SwingWorker and its thread management,
 * Used for running the tests and sending the results of the tests to the view
 */
class TestWorker extends SwingWorker<Boolean, String>{
    private final TesterModel testClass;
    private final TesterView view;

    /**
     * Creates a new worker
     * @param testClass the model of a class to be run
     * @param view where to print the results to
     */
    TestWorker(TesterModel testClass, TesterView view){
        this.testClass = testClass;
        this.view = view;
    }

    /**
     * The main worker thread, runs the tests and (if not cancelled) prints them
     * to the view
     * @return true if running, false if cancelled
     */
    @Override
    protected Boolean doInBackground() {
        for(Method method : testClass.getMethods()) {
            ResultObject result = testClass.run(method);
            if(isCancelled()){
                return false;
            }else{
                publish(result.formatString());
            }
        }
        return true;
    }

    /**
     * called by the background thread to print Strings in the main EDT thread
     * @param chunks the result strings from the tests
     */
    @Override
    protected void process(List<String> chunks) {
        for (String result : chunks) {
            this.view.print(result);
        }
    }

    /**
     * After the worker is done, it prints all the results from the given test
     * to the view.
     */
    @Override
    protected void done(){
        this.view.print(testClass.formatResults());
    }
}