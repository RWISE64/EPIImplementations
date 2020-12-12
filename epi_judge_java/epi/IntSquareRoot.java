package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class IntSquareRoot {
    @EpiTest(testDataFile = "int_square_root.tsv")

    /*
    Just use a binary search to find the largest integer root
    Could have potentially set r to be some fraction of k, but this way avoids potential edge cases without really
    adding too much runtime as r will likely be divided by two anyway.
    Runtime: O(logk)
    Space: O(1)
     */
    public static int squareRoot(int k) {
        int l = 0, r = k;
        // 1 should be the lowest possible solution
        int root = 0;
        // Using l <= r for the cases that our solution = l = r
        while (l <= r) {
            // == (l + r) / 2, but avoids overflow
            long m = l + ((r - l) / 2);
            long square = m * m;
            // Just return if we found an exact match
            if (square == k)
                return  (int) m;
            // Update root if square < k (valid solution), continue right of m
            else if (square < k) {
                root = Math.max(root, (int) m);
                l = (int) m + 1;
            }
            // If square > k need smaller square => continue left of m
            else
                r = (int) m - 1;
        }
        return root;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IntSquareRoot.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
