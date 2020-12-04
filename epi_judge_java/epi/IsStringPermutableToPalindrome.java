package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.HashSet;

public class IsStringPermutableToPalindrome {
    @EpiTest(testDataFile = "is_string_permutable_to_palindrome.tsv")

    /*
    Second Implementation, trying book sol + chars() since chars() is new to me
    Just uses a HashSet to track number of odd chars
    Runtime: O(n), just iterates through the string
    Space: O(k), where k = number of unique chars in s
    Could technically be O(1) if we are only dealing with lowercase letters, since there'd be 26 things stored max
    I guess you could apply that logic to the set of all possible characters, so it's a bit flawed
     */
    public static boolean canFormPalindrome(String s) {
        // Need to do integer HashSet since chars() provides char codes instead
        HashSet<Integer> oddChars = new HashSet();
        // Loop through s, updating oddChars
        s.chars().forEach((c) -> {
            if (oddChars.contains(c))
                oddChars.remove(c);
            else
                oddChars.add(c);
        });
        // Palindromes can either have one or zero odd counts chars
        return (oddChars.size() <= 1);
    }


    /*
    Initial implementation
    Just uses a hashmap to track the counts of each char
    Uses numOdd to track the number of odd counts
    Runtime: O(n), just iterates through the string
    Space: O(k), where k = number of unique chars in s
    Could technically be O(1) if we are only dealing with lowercase letters, since there'd be 26 things stored max
    I guess you could apply that logic to the set of all possible characters, so it's a bit flawed
     */
//    public static boolean canFormPalindrome(String s) {
//        int numOdd = 0;
//        // Could probably just store a boolean for whether each char is even or odd to save space
//        HashMap<Character, Integer> charCounts = new HashMap();
//        // Loop through s, tracking counts of each char and updating how many odd counts there are
//        for (int i = 0; i < s.length(); i++) {
//            char cur = s.charAt(i);
//            charCounts.put(cur, charCounts.getOrDefault(cur, 0) + 1);
//            numOdd += (charCounts.get(cur) % 2 == 1) ? 1 : -1;
//        }
//        // Palindromes can either have one or zero odd counts chars
//        return (numOdd <= 1);
//    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsStringPermutableToPalindrome.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
