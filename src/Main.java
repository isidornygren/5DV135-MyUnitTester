/**
 * @version 1.0
 * @author Isidor Nygren
 */

public class Main {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            TestWindow window = new TestWindow("MyUnitTester");
            window.setVisible(true);
        });
    }
}
