package _5dv135.assignment1;

/**
 * @version 1.0
 * @author Isidor Nygren
 */

public class Main {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
           public void run(){
               TestWindow window = new TestWindow("MyUnitTester");
           }
        });
    }
}
