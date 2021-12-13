import java.util.Scanner;
import java.lang.Math;

public class PowCalc {
    private static final int number1 = 12345;
    private static final int number2 = 2345;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the operation you wish to apply on both numbers");
        System.out.println("Operations: '+' (add), '-' (sub), '*' (mult), '/' (div), '^' (pow), 'sqrt' (sqrt)");
        String operand = sc.nextLine();
        processOperation(operand);
    }

    public static int operationAdd() {
        return number1 + number2;
    }

    public static int operationSubstract() {
        return number1 - number2;
    }

    public static int operationMultiply() {
        return number1 * number2;
    }

    public static int operationDivide() {
        return number1 / number2;
    }

    public static int operationSquare() {
        return number1 * number1;
    }

    public static int operationSquareRoot() {
        return (int)(Math.sqrt(Double.valueOf(number1)));
    }

    public static void processOperation(String operand) {
        if (operand.equals("+")) {
            printResult(operationAdd());
        } else if (operand.equals("-")) {
            printResult(operationSubstract());
        } else if (operand.equals("*")) {
            printResult(operationMultiply());
        } else if (operand.equals("/")) {
            printResult(operationDivide());
        } else if (operand.equals("^")) {
            printResult(operationSquare());
        } else if (operand.equals("sqrt")) {
            printResult(operationSquareRoot());
        } else {
            System.out.println("Invalid Operation");
        }
    }

    public static void printResult(int result) {
        System.out.println("The result is: " + result);
    }
}