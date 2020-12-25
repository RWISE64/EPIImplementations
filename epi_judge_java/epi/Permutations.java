package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;
import epi.test_framework.LexicographicalListComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permutations {
    @EpiTest(testDataFile = "permutations.tsv")

//    /*
//    Approach 1:
//    Time: O(n x n!) or something
//    Space: Staggering. Need to retry so that I'm not creating a brand new list for each split
//     */
//    public static List<List<Integer>> permutations(List<Integer> A) {
//        List<List<Integer>> res = new ArrayList<>();
//        generatePermutations(new ArrayList<>(), A, res);
//        return res;
//    }
//
//    private static void generatePermutations(List<Integer> cur, List<Integer> rem, List<List<Integer>> res) {
//        if (rem.size() == 0) {
//            // Add a copy of cur to avoid accidentally changing the res later
//            res.add(new ArrayList<>(cur));
//        }
//        else {
//            for (int i = 0; i < rem.size(); i++) {
//                int c = rem.get(i);
//                cur.add(c);
//                List<Integer> newRem = new ArrayList<>(rem);
//                newRem.remove((Integer) c);
//                generatePermutations(cur, newRem, res);
//                cur.remove(cur.size() - 1);
//            }
//        }
//    }

    /*
    Approach 1: Swap used numbers to the front, track where unused numbers are
    Time: O(n x n!) or something
    Space: Still quite a bit.
     */
    public static List<List<Integer>> permutations(List<Integer> A) {
        List<List<Integer>> res = new ArrayList<>();
        generatePermutations(A, 0, res);
        return res;
    }

    private static void generatePermutations(List<Integer> A, int pos, List<List<Integer>> res) {
        if (pos == A.size() - 1) {
            res.add(new ArrayList<>(A));
        }
        else {
            for (int i = pos; i < A.size(); i++) {
                // Swap current space to the end (of the current permutation space)
                Collections.swap(A, pos, i);
                generatePermutations(A, pos + 1, res);
                // Swap it back
                Collections.swap(A, pos, i);
            }
        }
    }

    @EpiTestComparator
    public static boolean comp(List<List<Integer>> expected,
                               List<List<Integer>> result) {
        if (result == null) {
            return false;
        }
        for (List<Integer> l : expected) {
            Collections.sort(l);
        }
        expected.sort(new LexicographicalListComparator<>());
        for (List<Integer> l : result) {
            Collections.sort(l);
        }
        result.sort(new LexicographicalListComparator<>());
        return expected.equals(result);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "Permutations.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
