/**
 * A basic class created to be tested by the MyUnitTester system
 * @version 1.0
 * @author Isidor Nygren
 */

public class Test2 implements TestClass {
    private Integer four, twenty;

    public Test2() {
    }

    public void setUp() {
        four = 4;
        twenty = 20;
    }

    public void tearDown() {
        four = null;
        twenty = null;
    }

    // This method should not be run
    public boolean thisIsNotATest(){
        System.out.println("Error: This should not have happened.");
        return false;
    }

    // This method should also not run
    public boolean testArgument(String arg){
        System.out.println("Error: This should not have happened.");
        return false;
    }

    //Test that should succeed
    public boolean testInitialisation() {
        return (four == 4 && twenty == 20);
    }

    //Test that should succeed
    public boolean testIntegerToString() {
        return (four.toString() + twenty.toString()).equals("420");
    }

    //Test that should fail
    public boolean testIntegerToStringToNumber() {
        Object string = (four.toString() + twenty.toString());
        return (string instanceof Integer);
    }

    //Test that should get an exception fail
    public boolean testIntegerParse(){
        return (new Integer("four-twenty") == 420);
    }

    //Test that should succeed
    public boolean testMultiplication() {
        return (four*100 + twenty == 420);
    }

    //Test that should fail
    public boolean testDivision() {
        return (four / twenty == 420);
    }

    //Test that should succeed
    public boolean testMathPower() {
        return (Math.pow(twenty,four) - 159580 == 420);
    }

}