package Controller;
import Model.ResultObject;
import Model.TesterModel;
import View.TesterView;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;

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
            view = new TesterView(this, "MyUnitTester");
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

        try{
            /* Check so no worker is running in the background already */
            if(this.worker != null){
                this.worker.cancel(true);
            }

            TesterModel test = new TesterModel(className);
            this.view.clearText();
            this.view.print("Running " + className + ":\n\n");
            this.view.print(test.formatFormattingErrors());

            /* Run the tests for the class in a separate thread */
            this.worker = new TestWorker(test, this.view);
            this.worker.execute();

        }catch(ClassNotFoundException e){
            view.errorMessage("Error", "Error: Class " + className + " not found.\n" + e + "\n");
        }catch(ClassFormatException e){
            view.errorMessage("Error", "Error: Wrong class format.\n" + e + "\n");
        }catch(NoSuchMethodException e){
            view.errorMessage("Error", "Could not find constructor for test\n" + e);
        }catch(InstantiationException e){
            view.errorMessage("Error", "Could not create new instance of test class.\n" +
                    "Is the Class constructor correctly formatted?\n" + e);
        }catch(IllegalAccessException e){
            view.errorMessage("Error", "No access to class file\n" + e);
        }
    }
}

/**
 * A worker extending the SwingWorker and its threadmanagement,
 * Used for running the tests and sending the results of the tests to the view
 */
class TestWorker extends SwingWorker<Boolean, String>{
    private TesterModel testClass;
    private TesterView view;

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
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        for(Method method : testClass.getMethods()) {
            ResultObject result = testClass.run(method);
            if(isCancelled()){
                return false;
            }else{
                this.view.print(result.formatString());
            }
        }
        return true;
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