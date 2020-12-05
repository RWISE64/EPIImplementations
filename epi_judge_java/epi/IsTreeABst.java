package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class IsTreeABst {
    @EpiTest(testDataFile = "is_tree_a_bst.tsv")


    /*
    Second implementation, wanted to try out an in-order traversal
     */
    public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
        // Just using an array to track a single value
        // Wanted to make sure the min stays updated between calls, thus use array since it's passed by reference
        int[] min = new int[] {Integer.MIN_VALUE};
        return isBSTRec(tree, min);
    }

    public static boolean isBSTRec(BinaryTreeNode<Integer> node, int[] min) {
        if (node == null)
            return true;
        // Check left
        if (!isBSTRec(node.left, min))
            return false;
        // Return false if current node is greater than min (since we are traversing in-order)
        // Again, pretty sure BSTs don't allow dups but the solution implies they do
        if (node.data < min[0])
            return false;
        // Set min to be current node's value
        min[0] = node.data;
        // Check right
        if (!isBSTRec(node.right, min))
            return false;
        return true;
    }

//    /*
//    First implementation, just recursively iterate while tracking max and min
//    Time Complexity: O(n), where n = # of nodes
//    Space Complexity: O(h), where h = height. Allegedly at least. I know the storage is based on the recursive stack calls,
//    but I can't quite wrap my head around why that'd be limited to the height. I just think the number of calls would be
//    much greater than h if two calls might be made per isBSTRec call
//     */
//    public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
//        // This kinda fails if the value of root is the max or min int, but we're gonna ignore that
//        return isBSTRec(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
//    }
//
//    public static boolean isBSTRec(BinaryTreeNode<Integer> root, int min, int max) {
//        if (root == null)
//            return true;
//        // I thought BSTs can't have duplicate data, but it seems like they can according to the tests... go figure
//        if (root.data < min || root.data > max)
//            return false;
//        return isBSTRec(root.left, min, root.data) && isBSTRec(root.right, root.data, max);
//    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsTreeABst.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
