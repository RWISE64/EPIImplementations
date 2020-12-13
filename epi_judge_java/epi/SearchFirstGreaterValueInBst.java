package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class SearchFirstGreaterValueInBst {

    /*
    Just use BST ordering to traverse just height at most
    If current node is less than or equal to k, we just explore the right tree
    Otherwise, we save the current node and explore left
    This works since exploring left ensures that any new node > k is smaller than the previous res
    Time: O(h) - just explores the height of the tree in the worst case
    Space: O(1)
     */
    public static BstNode<Integer> findFirstGreaterThanK(BstNode<Integer> tree,
                                                         Integer k) {
        BstNode<Integer> res = null;
        while (tree != null) {
            if (tree.data <= k)
                tree = tree.right;
            else {
                res = tree;
                tree = tree.left;
            }
        }
        return res;
    }

    @EpiTest(testDataFile = "search_first_greater_value_in_bst.tsv")
    public static int findFirstGreaterThanKWrapper(BstNode<Integer> tree,
                                                   Integer k) {
        BstNode<Integer> result = findFirstGreaterThanK(tree, k);
        return result != null ? result.data : -1;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SearchFirstGreaterValueInBst.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
