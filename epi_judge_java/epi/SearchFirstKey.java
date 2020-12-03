package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

public class SearchFirstKey {
    @EpiTest(testDataFile = "search_first_key.tsv")

    /*
    Time Complexity: O(logn) (should be every time, not just worst case)
    Space Complexity: O(1)
     */
    public static int searchFirstOfK(List<Integer> A, int k) {
        int l = 0, r = A.size() - 1, m = 0, i = Integer.MAX_VALUE;
        // Loop until the range between l and r is less than one
        while (l <= r) {
            // Equivalent to (l + r) / 2, but avoids integer overflow
            m = l + (r - l) / 2;
            int mid = A.get(m);
            if (mid == k) {
                // Set i to min of i and m. Probably unnecessary, but better safe than sorry
                i = Math.min(i, m);
                // Search left subarray instead of stopping
                // Done to ensure we get the first instance, rather than any instance
                r = m - 1;
            }
            // k is in right subarray
            else if (mid < k) {
                l = m + 1;
            }
            // k is in left subarray
            else {
                r = m - 1;
            }
        }
        // Return -1 if i wasn't updated (kinda fails if m == Integer.MAX_VALUE...)
        return (i == Integer.MAX_VALUE) ? -1 : i;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SearchFirstKey.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
