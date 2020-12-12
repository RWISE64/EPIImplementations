package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

public class TwoSortedArraysMerge {

    /*
    Just fill from the end to avoid moving elements multiple times
    Since there is just enough space, we won't need to swap elements if filling from the back
    Time: O(n + m) as we just pass through each array once
    Space: O(1) (other than the space from the original lists)
     */
    public static void mergeTwoSortedArrays(List<Integer> A, int m,
                                            List<Integer> B, int n) {
        // Tracks the last unsorted spot in the array
        int end = m + n - 1;
        m--;
        n--;
        // Repeat until we have gone through the entire final array
        while (end >= 0) {
            // Use element at the end of A if B is empty or the element at A is greater than B's
            if (m >= 0 && (n < 0 || A.get(m) >= B.get(n)))
                A.set(end--, A.get(m--));
            else
                A.set(end--, B.get(n--));
        }
    }

    @EpiTest(testDataFile = "two_sorted_arrays_merge.tsv")
    public static List<Integer>
    mergeTwoSortedArraysWrapper(List<Integer> A, int m, List<Integer> B, int n) {
        mergeTwoSortedArrays(A, m, B, n);
        return A;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TwoSortedArraysMerge.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
