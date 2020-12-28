package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

public class TreeFromPreorderInorder {
    @EpiTest(testDataFile = "tree_from_preorder_inorder.tsv")

    /*
    My implementation is definitely not without its flaws...
    Essentially selects the next root from the start of preorder, then looks for
    to the left then right within inorder
    The time complexity might be O(n^2) worst-case when the tree is skewed entirely left
    Otherwise it should come out to around O(nlogn) (maybe)
     */
    public static BinaryTreeNode<Integer>
    binaryTreeFromPreorderInorder(List<Integer> preorder, List<Integer> inorder) {
        return binaryTreeRec(preorder, inorder, 0, inorder.size() - 1);
    }

    private static BinaryTreeNode<Integer> binaryTreeRec(List<Integer> preorder, List<Integer> inorder,
                                                         int inStart, int inEnd) {
        // Base case
        if (preorder.size() == 0 || inStart < 0 || inEnd >= inorder.size())
            return null;
        int cur = preorder.get(0);
        int m = -1;
        // See if the current root is within our range in inorder
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder.get(i) == cur) {
                m = i;
                break;
            }
        }
        if (m == -1)
            return null;
        // Move preorder forward if we actually found the element
        preorder.remove(0);
        BinaryTreeNode<Integer> root = new BinaryTreeNode(cur);
        // Build the left and right trees recursively
        root.left = binaryTreeRec(preorder, inorder, inStart, m - 1);
        root.right = binaryTreeRec(preorder, inorder, m + 1, inEnd);
        return root;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TreeFromPreorderInorder.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
