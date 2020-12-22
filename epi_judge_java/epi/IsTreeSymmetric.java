package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class IsTreeSymmetric {
    @EpiTest(testDataFile = "is_tree_symmetric.tsv")

    /*
    Pretty straight-forward. Use a recursive method to iterate the mirrored sides, making comparisons on each
    Time: O(n), where n is the number of nodes in tree
    Space: O(h), where h is the height of the mirrored section (won't follow a long un-mirrored path)
     */
    public static boolean isSymmetric(BinaryTreeNode<Integer> tree) {
        return (tree == null) ? true : checkSymmetryRec(tree.left, tree.right);
    }

    // This recursive method essentially iterates through the tree with mirrored iterators
    private static boolean checkSymmetryRec(BinaryTreeNode<Integer> left, BinaryTreeNode<Integer> right) {
        // Base cases, null checks then data comparison
        if (left == null && right == null)
            return true;
        else if (left == null || right == null)
            return false;
        if (left.data != right.data)
            return false;
        // Check for symmetry for the left and right mirror children
        return checkSymmetryRec(left.left, right.right) && checkSymmetryRec(left.right, right.left);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsTreeSymmetric.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
