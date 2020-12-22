package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Map;
import java.util.Stack;
import java.util.function.ToIntBiFunction;

public class EvaluateRpn {
    @EpiTest(testDataFile = "evaluate_rpn.tsv")

    /*
    Just use a stack to store the integer values
    Any calculated value is thrown back onto the stack to allow it to be used in the next operation
    Time: O(n), where n is the number of values in the expression (number or operand)
    Space: O(n), since I split the expression based on commas
     */
    public static int eval(String expression) {
        String[] vals = expression.split(",");
        // Trying out the books way of saving functions to avoid some big if statement or switch
        // Note that y comes before x, due to the reverse ordering of a stack
        final Map<String, ToIntBiFunction<Integer, Integer>> OPERATORS = Map.of(
                "+", (y, x) -> x + y,
                "-", (y, x) -> x - y,
                "*", (y, x) -> x * y,
                "/", (y, x) -> x / y
        );
        Stack<Integer> s = new Stack();
        for (String val : vals) {
            if (OPERATORS.get(val) != null) {
                // Can't perform operation if there aren't two values in the stack
                // Wasn't given an invalid rpn return value, so returning -1
                if (s.size() < 2)
                    return -1;
                int val1 = s.pop();
                int val2 = s.pop();
                // Add res back to the stack so that it can be used in the next operation
                s.push(OPERATORS.get(val).applyAsInt(val1, val2));
            }
            else {
                s.push(Integer.parseInt(val));
            }
        }
        // If there is more than 1 value, the rpn wasn't valid
        return (s.size() == 1) ? s.pop() : -1;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "EvaluateRpn.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
