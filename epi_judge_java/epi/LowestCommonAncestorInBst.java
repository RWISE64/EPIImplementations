package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

public class LowestCommonAncestorInBst {

    // Input nodes are nonempty and the key at s is less than or equal to that at
    // b.
//    /*
//    Implementation 1:
//    Can avoid traversing all the way down to s and b by just using the BST property
//    Time: O(h) - still need to go down to h - 1 worst-case
//    Space: O(h), due to stack storage
//     */
//    public static BstNode<Integer>
//    findLca(BstNode<Integer> tree, BstNode<Integer> s, BstNode<Integer> b) {
//        // If current node matches s or b, it must be the LCA (a node is an ancestor of itself)
//        if (tree.data == s.data || tree.data == b.data)
//            return tree;
//        // Is also the LCA if s and b occur on different sides of tree
//        else if ((tree.data > s.data && tree.data < b.data) || (tree.data < s.data && tree.data > b.data))
//            return tree;
//        // Otherwise, recursively progress down the left or right side
//        else if (tree.data > s.data)
//            return findLca(tree.left, s, b);
//        return findLca(tree.right, s, b);
//    }

    /*
    Implementation 2:
    Trying to avoid the recursive stack use this time.
    Time: O(h)
    Space: O(1)
     */
    public static BstNode<Integer>
    findLca(BstNode<Integer> tree, BstNode<Integer> s, BstNode<Integer> b) {
        // If current node matches s or b, it must be the LCA (a node is an ancestor of itself)
        while (tree.data != s.data && tree.data != b.data) {
            // Is an LCA if s and b occur on different sides of tree
            if ((tree.data > s.data && tree.data < b.data) || (tree.data < s.data && tree.data > b.data))
                break;
            // Otherwise both nodes are on one side of tree => advance down that side
            else if (tree.data > s.data)
                tree = tree.left;
            else
                tree = tree.right;
        }
        return tree;
    }

    @EpiTest(testDataFile = "lowest_common_ancestor_in_bst.tsv")
    public static int lcaWrapper(TimedExecutor executor, BstNode<Integer> tree,
                                 Integer key0, Integer key1) throws Exception {
        BstNode<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
        BstNode<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

        BstNode<Integer> result = executor.run(() -> findLca(tree, node0, node1));

        if (result == null) {
            throw new TestFailure("Result can't be null");
        }
        return result.data;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LowestCommonAncestorInBst.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
