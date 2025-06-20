package task2;

import java.util.Stack;

import static task2.Main.infixToPostfix;
import static task2.Operation.applyOperation;

public class Utils {

    static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperation(token.charAt(0), a, b));
            }
        }

        return stack.pop();
    }

    public static double evaluate(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

}
