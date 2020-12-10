package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TreeLevelOrder {
    @EpiTest(testDataFile = "tree_level_order.tsv")


    /*
    Just use a queue to ensure to traverse the tree by level
    Used a spacer node to keep track of levels
    Time: O(n), iterates over each node once
    Space: O(l), where l = the maximum number of nodes in one level
     */
    public static List<List<Integer>>
    binaryTreeDepthOrder(BinaryTreeNode<Integer> tree) {
        List<List<Integer>> levels = new ArrayList();
        List<Integer> level = new ArrayList();
        if (tree == null)
            return levels;
        Deque<BinaryTreeNode<Integer>> queue = new ArrayDeque();
        // Use spacer to keep track of when a level has been completed
        BinaryTreeNode<Integer> spacer = new BinaryTreeNode(0, null, null);
        queue.offer(tree);
        queue.offer(spacer);
        while (!queue.isEmpty()) {
            BinaryTreeNode<Integer> cur = queue.poll();
            // If spacer is reached, add level and create a new level
            if (cur == spacer) {
                levels.add(level);
                level = new ArrayList();
                // Only readd spacer if there are more nodes to go through
                if (!queue.isEmpty())
                    queue.offer(spacer);
            }
            // Otherwise add the data to the level and add children to the queue
            else {
                level.add(cur.data);
                if (cur.left != null)
                    queue.offer(cur.left);
                if (cur.right != null)
                    queue.offer(cur.right);
            }
        }
        return levels;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TreeLevelOrder.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
