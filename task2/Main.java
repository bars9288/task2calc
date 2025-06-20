package task2;

import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

import static task2.Operation.applyOperation;
import static task2.Utils.evaluate;
import static task2.Utils.evaluatePostfix;

public class Main {

    private static final Map<Character, Integer> PRIORITY = new HashMap<>();
    static {{
        PRIORITY.put('+', 1);
        PRIORITY.put('-', 1);
        PRIORITY.put('*', 2);
        PRIORITY.put('/', 2);
        PRIORITY.put('^', 3);
    }}

    static String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                while (i < infix.length() &&
                        (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    output.append(infix.charAt(i));
                    i++;
                }
                output.append(' ');
                i--;
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(' ');
                }
                stack.pop();
            }
            else if (isOperator(c)) {

                if (c == '-' && (i == 0 || infix.charAt(i - 1) == '(' || isOperator(infix.charAt(i - 1)))) {
                    output.append("0 ");
                }

                while (!stack.isEmpty() && stack.peek() != '(' &&
                        (PRIORITY.get(stack.peek()) > PRIORITY.get(c) ||
                                (PRIORITY.get(stack.peek()) == PRIORITY.get(c) && c != '^'))) {
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



    private static boolean isOperator(char c) {
        return PRIORITY.containsKey(c);
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