package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ThreeSum {
    @EpiTest(testDataFile = "three_sum.tsv")

    /*
    Implementation 2: Trying some book suggestions / tricks
    Time: O(n^2) - still calls hasTwoSum for each element in A
    Space: O(1) - sorting allows hasTwoSum to use O(1) space
     */
    public static boolean hasThreeSum(List<Integer> A, int t) {
        // Sort the array first - allows us to just search for combos in O(n) time with O(1) space
        // Requires O(nlogn) to sort, but is dominated by the overall O(n^2) runtime
        Collections.sort(A);
        // Some fancy stream stuff - pretty new to me
        // Essentially a forEach on A, where it will return true if hasTwoSum ever returns true
        // Want to return true if there is a twoSum within A that equals the total minus the third value
        return A.stream().anyMatch(a -> hasTwoSum(A, t - a));
    }

    /*
    Trying to implement this fancy twoSum rather than using the library
    Expects A to be sorted from smallest to largest
    Runs in O(n) time since it uses the fact that A is sorted to only do n comparisons
     */
    public static boolean hasTwoSum(List<Integer> A, int t) {
        int l = 0, r = A.size() - 1;
        // Runs until l and r pass each other
        // Try with l == r since we are allowed duplicates
        while (l <= r) {
            int sum = A.get(l) + A.get(r);
            // Return if sum is correct
            if (sum == t)
                return true;
            // Need to decrease r if sum is too large
            else if (sum > t)
                r--;
            // Need to increase l if sum is too small
            else
                l++;
        }
        return false;
    }


//    /*
//    Implementation 1
//    I try to include an element, then check if a twoSum exists with the remaining amount
//    Time: O(n^2), where n = A.size(). hasThreeSum calls hasTwoSum for all elements in A, and hasTwoSum = O(n)
//    Space: O(n) or O(n^2). Not quite sure. It's O(n) if we are talking about the max amount of space the program uses
//    at once. hasTwoSum uses O(n) space each time, but that memory should be freed up after each call. Since hasTwoSum
//    is called n times, it would be O(n^2) if we count the memory used at any point. Can't find a def. that clears this
//    up unfortunately...
//     */
//    public static boolean hasThreeSum(List<Integer> A, int t) {
//        // For each entry in A, add to total then check if twoSum exists
//        for (int i = 0; i < A.size(); i++) {
//            if (hasTwoSum(A, i, t - A.get(i)))
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean hasTwoSum(List<Integer> A, int start, int t) {
//        // Uses a HashSet to track the difference between A(i) and t
//        // If A(i) exists in diffs, then some A(i) + A(j) = t
//        HashSet<Integer> diffs = new HashSet();
//        // Skip up to start to avoid retrying past numbers
//        for (int i = start; i < A.size(); i++) {
//            // Add current number first since duplicates are allowed
//            // This allows a solution like A.get(i) + A.get(i)
//            diffs.add(t - A.get(i));
//            if (diffs.contains(A.get(i)))
//                return true;
//        }
//        return false;
//    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ThreeSum.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
