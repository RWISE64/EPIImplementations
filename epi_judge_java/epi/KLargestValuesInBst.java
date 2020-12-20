package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class KLargestValuesInBst {
    @EpiTest(testDataFile = "k_largest_values_in_bst.tsv")

    /*
    I just preformed a traversal with the ordering right -> root -> left
    This should iterate over the nodes in reverse order
    I passed along a list to be filled, and used k to track the index to fill in the list
    Time: O(n). Depends on k, but could need to traverse the entire tree to get to k
    Space: O(h), where h is the height of the tree (due to stack storage)
     */
    public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
        // Using Integer wrapper so conversion from array to list doesn't complain
        // Additionally used a static-sized array, so that I don't deal with potential O(k) shifts when adding elements
        // to the front of the list
        Integer[] list = new Integer[k];
        findKLargestInBstRec(tree, k, list);
        return Arrays.asList(list);
    }

    private static int findKLargestInBstRec(BstNode<Integer> tree, int k, Integer[] list) {
        // Start bubbling up if node is null or if k has reached 0
        // Just stop right away if k = 0 to avoid unnecessary exploration
        if (tree == null || k == 0)
            return k;
        // Explore right, since right should be greater than the root
        int cur = findKLargestInBstRec(tree.right, k, list);
        // Bubble up if k has reached 0
        if (cur == 0)
            return cur;
        list[--cur] = tree.data;
        // Explore left after exploring the root
        return findKLargestInBstRec(tree.left, cur, list);
    }

    @EpiTestComparator
    public static boolean comp(List<Integer> expected, List<Integer> result) {
        if (result == null) {
            return false;
        }
        Collections.sort(expected);
        Collections.sort(result);
        return expected.equals(result);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "KLargestValuesInBst.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
