package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class IsTreeBalanced {

    // Need extra class to pair height and boolean for balanced
    // Moments like this make me miss randomly creating JS objects
    public static class Balance {
        public boolean bal;
        public int height;

        public Balance(boolean bal, int height) {
            this.bal = bal;
            this.height = height;
        }
    }


    @EpiTest(testDataFile = "is_tree_balanced.tsv")

    public static boolean isBalanced(BinaryTreeNode<Integer> tree) {
        return balRec(tree).bal;
    }

    public static Balance balRec(BinaryTreeNode<Integer> node) {
        // Might be doing the wrong height, but doesn't matter if all leaves have the wrong height...
        if (node == null)
            return new Balance(true, 0);
        Balance left = balRec(node.left);
        // Just bubble up the false if false, doesn't other values don't matter anymore
        if (!left.bal)
            return left;
        Balance right = balRec(node.right);
        if (!right.bal)
            return right;
        // Unbalanced if height diff > 1
        boolean bal = Math.abs(left.height - right.height) <= 1;
        int height = 1 + Math.max(left.height, right.height);
        // Heh... new Balance... like the brand... ha ha ha I probs need sleep
        return new Balance(bal, height);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
