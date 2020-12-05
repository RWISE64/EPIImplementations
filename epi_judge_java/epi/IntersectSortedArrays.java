package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IntersectSortedArrays {
    @EpiTest(testDataFile = "intersect_sorted_arrays.tsv")

    /*
    You could theoretically optimize based on the various lengths of A and B, but I'm not gonna
    I find the two iterators solution much simpler and likely faster in many cases
    Time Complexity: O(n+m), where n = A.size(), m = B.size()
    Space Complexity: O(1)
     */
    public static List<Integer> intersectTwoSortedArrays(List<Integer> A,
                                                         List<Integer> B) {
        int iterA = 0, iterB = 0;
        ArrayList<Integer> merged = new ArrayList();
        while (iterA < A.size() && iterB < B.size()) {
            int curA = A.get(iterA), curB = B.get(iterB);
            // If equal, add to set and advance both
            if (curA == curB) {
                // Ensures that we don't add duplicates to the results
                if (merged.isEmpty() || merged.get(merged.size() - 1) != curA)
                    merged.add(curA);
                iterA++;
                iterB++;
            }
            // if curA is smaller, advance iterA
            else if (curA < curB) {
                iterA++;
            }
            // if curB is smaller, advance iterB
            else if (curB < curA) {
                iterB++;
            }
        }
        // Just return hashset afterwards, rest of A or B shouldn't be within the other
        return merged;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IntersectSortedArrays.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
