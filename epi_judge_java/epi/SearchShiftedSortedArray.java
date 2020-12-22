package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

public class SearchShiftedSortedArray {
    @EpiTest(testDataFile = "search_shifted_sorted_array.tsv")

    /*
    I initially thought elements could be repeated, which is why I did a recursive approach like this
    Tries to do a binary search on each section, but stops the propagation if the left is smaller than the right
    Time: O(logn) if there are no duplicates, O(n) worst-case if there are
    Space: O(logn) - could have been O(1) if I didn't solve this recursively, which would be possible if there are no dups
     */
    public static int searchSmallest(List<Integer> A) {
        return searchSmallestRec(A, 0, A.size() - 1);
    }

    private static int searchSmallestRec(List<Integer> A, int l, int r) {
        // Base Case - if the left is less than the right, in this section the left is the smallest
        // Also return if l = r
        if (A.get(l) < A.get(r) || l == r)
            return l;
        int m = l + (r - l) / 2;
        int minl = searchSmallestRec(A, l, m);
        int minr = searchSmallestRec(A, m + 1, r);
        return (A.get(minl) < A.get(minr)) ? minl : minr;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SearchShiftedSortedArray.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
