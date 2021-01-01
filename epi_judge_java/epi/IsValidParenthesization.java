package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class IsValidParenthesization {
    @EpiTest(testDataFile = "is_valid_parenthesization.tsv")

    /*
    Just use a stack to track the most recently open parenthesis
    Tried to use some extra data structures to cut down on the if statements
    Time: O(n), where n = s.length()
    Space: O(n) - stores up to half of s
     */
    public static boolean isWellFormed(String s) {
        ArrayDeque<Character> openParenthesisStack = new ArrayDeque<>();
        HashSet<Character> openParenthesis = new HashSet<>();
        openParenthesis.addAll(Arrays.asList('{', '[', '('));
        HashMap<Character, Character> closedParenthesisToOpen = new HashMap<>();
        closedParenthesisToOpen.put('}', '{');
        closedParenthesisToOpen.put(']', '[');
        closedParenthesisToOpen.put(')', '(');
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (openParenthesis.contains(cur)) {
                openParenthesisStack.push(cur);
            }
            // Invalid if there are no open parenthesis or the current closing parenthesis doesn't equal the most recently opened
            else if (openParenthesisStack.isEmpty() || closedParenthesisToOpen.get(cur) != openParenthesisStack.pop()) {
                return false;
            }
        }
        // False if there are some remaining open parenthesis
        return openParenthesisStack.isEmpty();
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsValidParenthesization.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
