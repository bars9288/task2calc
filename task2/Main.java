package task2;

import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<Character, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put('+', 1);
        PRECEDENCE.put('-', 1);
        PRECEDENCE.put('*', 2);
        PRECEDENCE.put('/', 2);
        PRECEDENCE.put('^', 3);
    }

    public static double evaluate(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                // Обработка чисел (включая десятичные)
                while (i < infix.length() &&
                        (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    output.append(infix.charAt(i));
                    i++;
                }
                output.append(' '); // Разделитель между числами
                i--; // Коррекция индекса
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(' ');
                }
                stack.pop(); // Удаляем '(' из стека
            }
            else if (isOperator(c)) {
                // Обработка унарного минуса
                if (c == '-' && (i == 0 || infix.charAt(i - 1) == '(' || isOperator(infix.charAt(i - 1)))) {
                    output.append("0 "); // Добавляем ноль для унарного минуса
                }

                while (!stack.isEmpty() && stack.peek() != '(' &&
                        (PRECEDENCE.get(stack.peek()) > PRECEDENCE.get(c) ||
                                (PRECEDENCE.get(stack.peek()) == PRECEDENCE.get(c) && c != '^'))) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }

        return output.toString().trim();
    }

    private static double evaluatePostfix(String postfix) {
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

    private static boolean isOperator(char c) {
        return PRECEDENCE.containsKey(c);
    }

    private static double applyOperation(char op, double a, double b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            case '^': return Math.pow(a, b);
            default: throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
            try {
                System.out.printf("%s = %.2f%n", string, evaluate(string));
            } catch (Exception e) {
                System.out.println(string + " → Error: " + e.getMessage());
            }
        }
    }
}