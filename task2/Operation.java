package task2;

public class Operation {

    public static double applyOperation(char operator, double value1, double value2) {
        switch (operator) {
            case '+': return value1 + value2;
            case '-': return value1 - value2;
            case '*': return value1 * value2;
            case '/': {
                if (value2 == 0) throw new ArithmeticException("Division by zero");
                return value1 / value2;
            }
            case '^': return Math.pow(value1, value2);
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
