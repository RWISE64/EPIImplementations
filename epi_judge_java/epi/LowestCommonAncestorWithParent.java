package epi;

import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

public class LowestCommonAncestorWithParent {

    /*
    Just equalizes the depths of the two nodes then iterates up until the parent is found
    Time: O(d), where d = depth of the lowest node
    Space: O(1)
     */
    public static BinaryTree<Integer> lca(BinaryTree<Integer> node0,
                                          BinaryTree<Integer> node1) {
        int depth0 = getDepth(node0);
        int depth1 = getDepth(node1);
        // Equalize the depths
        while (depth0 != depth1) {
            if (depth0 > depth1) {
                node0 = node0.parent;
                depth0--;
            }
            else {
                node1 = node1.parent;
                depth1--;
            }
        }
        // Just iterate up until they are equal
        while (node0 != node1) {
            node0 = node0.parent;
            node1 = node1.parent;
        }
        return node0;
    }

    public static int getDepth(BinaryTree<Integer> node) {
        int depth = 0;
        while (node.parent != null) {
            depth++;
            node = node.parent;
        }
        return depth;
    }

    @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
    public static int lcaWrapper(TimedExecutor executor, BinaryTree<Integer> tree,
                                 Integer key0, Integer key1) throws Exception {
        BinaryTree<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
        BinaryTree<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

        BinaryTree<Integer> result = executor.run(() -> lca(node0, node1));

        if (result == null) {
            throw new TestFailure("Result can not be null");
        }
        return result.data;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LowestCommonAncestorWithParent.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
