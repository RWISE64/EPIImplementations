package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LevenshteinDistance {
    @EpiTest(testDataFile = "levenshtein_distance.tsv")

    /*
    Uses HashMap to store the best solutions the different lengths of A and B
    My solution is far from ideal, since I don't necessarily need to pass in new substrings each time
    Additionally, my HashMap is literally just a 2D array but stupid
    However, implementing it this way was easiest for me to understand, so I'm keeping it.
    Time: O(ab), where a = A.length(), b = b.length()
    Space: O(ab), since we pretty much form a 2D array of a x b but with extra steps
     */
    public static int levenshteinDistance(String A, String B) {
        // Stores the best solution for the two lengths
        // Could definitely have just used a 2D array... oops
        HashMap<List<Integer>, Integer> sols = new HashMap();
        // When both A and B are empty, 0 modifications are necessary
        sols.put(Arrays.asList(0, 0), 0);
        return levenRec(A, B, sols);
    }

    private static int levenRec(String A, String B, HashMap<List<Integer>, Integer> sols) {
        List<Integer> cur = Arrays.asList(A.length(), B.length());
        // Just return past solution if it exists
        if (sols.containsKey(cur))
            return sols.get(cur);
        // If either length is 0, will need to do Math.max(A.length(), B.length()) operations to correct (rems or ins)
        if (A.length() == 0 || B.length() == 0) {
            sols.put(cur, Math.max(A.length(), B.length()));
            return sols.get(cur);
        }
        // Calc the best solution - try sub, ins, and rem, keep best solution
        int sub = levenRec(A.substring(1), B.substring(1), sols);
        // Sub doesn't cost anything, unless first chars aren't equal
        if (A.charAt(0) != B.charAt(0))
            sub++;
        // Other operations will cost one
        int ins = 1 + levenRec(A, B.substring(1), sols);
        int rem = 1 + levenRec(A.substring(1), B, sols);
        // Store whichever operation resulted in the cheapest thing
        int min = Math.min(ins, Math.min(sub, rem));
        sols.put(cur, min);
        return min;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LevenshteinDistance.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
