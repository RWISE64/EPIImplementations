package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class KthLargestInArray {
    // The numbering starts from one, i.e., if A = [3,1,-1,2] then
    // findKthLargest(1, A) returns 3, findKthLargest(2, A) returns 2,
    // findKthLargest(3, A) returns 1, and findKthLargest(4, A) returns -1.

//    /*
//    Attempting Book Implementation: Split around a random pivot, get rid of entries before or after pivot
//    Couldn't quite get this to work - probably have some one-off error or something
//    Runtime: O(n) on average
//    Space: O(1)
//     */
//    @EpiTest(testDataFile = "kth_largest_in_array.tsv")
//    public static int findKthLargest(int k, List<Integer> A) {
//        int l = 0, r = A.size() - 1;
//        Random gen = new Random(0);
//        while (true) {
//            // Randomly select pivot
//            int p = gen.nextInt(r - l + 1) + l;
//            p = partionAroundIndex(l, r, p, A);;
//            // pivot is the kth largest element if there are k - 1 elements greater than or equal to p
//            if (p == k - 1)
//                return A.get(p);
//            // there are less than k - 1 elements greater than or equal to p => move right down
//            else if (p > k - 1)
//                r = p - 1;
//            else
//                l = p + 1;
//        }
//    }
//
//    private static int partionAroundIndex(int l, int r, int p, List<Integer> A) {
//        int pivot = A.get(p);
//        // If pivot is less than all elements, it will be placed at l
//        p = l;
//        // Set p to the end (r)
//        Collections.swap(A, p, r);
//        for (int i = l; i < r; i++) {
//            // If element goes before pivot, swap with pivot and iterate pivot
//            if (A.get(i) < pivot)
//                Collections.swap(A, p++, i);
//        }
//        // Set pivot back to its place
//        Collections.swap(A, p, r);
//        return p;
//    }

  /*
  Initial Approach: Use divide and conquer k times (yeah, it's bad.)
  Time to beat = O(nlogn) - just sort then select index k - 1
  Slightly better approach - perform k divide and conquer searches, removing the largest each time
  Runtime: O(klogn) - performs k searches of time logn
  Nevermind, turns out I'm dumb. This will actually be O(kn), as a d&c search is actually O(n)...
  Space: O(logn) - due to the amount of stack space used. O(n) if we consider the initial array.
   */
    @EpiTest(testDataFile = "kth_largest_in_array.tsv")
    public static int findKthLargest(int k, List<Integer> A) {
        int cur = 0;
        for (int i = 0; i < k; i++) {
            int maxInd = getLargestIndRec(0, A.size() - 1, A);
            cur = A.get(maxInd);
            A.remove(maxInd);
        }
        return cur;
    }

    private static int getLargestIndRec(int l, int r, List<Integer> A) {
        if (l == r)
            return l;
        int m = l + (r - l) / 2;
        int maxL = getLargestIndRec(l, m, A);
        int maxR = getLargestIndRec(m + 1, r, A);
        return (A.get(maxL) > A.get(maxR)) ? maxL : maxR;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "KthLargestInArray.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
