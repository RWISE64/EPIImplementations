package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

public class DoTerminatedListsOverlap {

    /*
    Initial Idea: Just store visited nodes in a HashSet, iterate through both lists watching for previously visited nodes
    Would have been O(n + m) time and space; wanted to try to reduce space.
    Insight: if they do overlap, they should end on the same node (as a node can't branch to different places)
    New Idea: Just iterate to the end of each list, compare the last node
    Time: O(n + m), where n is the size of l0 and m is the size of l1
    Space: O(1)
    So. The prompt just said to return whether or not there is a node that is shared, so hopefully I can just return the
    last node. Otherwise, need another new approach (or just use the old approach).
    New Approach: Track the length of the lists as well. If they share that last node, restart and advance the larger
    list until it has the same amount left, then iterate both together.
    Same time and space complexity, although I am thoroughly miffed that the prompt wasn't more specific.
     */
    public static ListNode<Integer>
    overlappingNoCycleLists(ListNode<Integer> l0, ListNode<Integer> l1) {
        ListNode<Integer> iter0 = l0, iter1 = l1;
        int size0 = 1, size1 = 1;
        if (iter0 == null || iter1 == null)
            return null;
        // Go through both lists, grabbing the size while at it
        while (iter0.next != null) {
            iter0 = iter0.next;
            size0++;
        }
        while (iter1.next != null) {
            iter1 = iter1.next;
            size1++;
        }
        // No shared part if the last nodes aren't equal
        if (iter0 != iter1)
            return null;
        // Set iter0 to be the larger of the two for simplicity
        if (size0 < size1) {
            iter0 = l1;
            iter1 = l0;
            int temp = size0;
            size0 = size1;
            size1 = temp;
        }
        else {
            iter0 = l0;
            iter1 = l1;
        }
        // Advance iter0 until iter1 and iter0 have equal remaining nodes
        while (size0 > size1) {
            size0--;
            iter0 = iter0.next;
        }
        // Iterate both until the nodes are equal
        while (iter0 != iter1) {
            iter0 = iter0.next;
            iter1 = iter1.next;
        }
        return iter0;
    }

    @EpiTest(testDataFile = "do_terminated_lists_overlap.tsv")
    public static void
    overlappingNoCycleListsWrapper(TimedExecutor executor, ListNode<Integer> l0,
                                   ListNode<Integer> l1, ListNode<Integer> common)
            throws Exception {
        if (common != null) {
            if (l0 != null) {
                ListNode<Integer> i = l0;
                while (i.next != null) {
                    i = i.next;
                }
                i.next = common;
            } else {
                l0 = common;
            }

            if (l1 != null) {
                ListNode<Integer> i = l1;
                while (i.next != null) {
                    i = i.next;
                }
                i.next = common;
            } else {
                l1 = common;
            }
        }

        final ListNode<Integer> finalL0 = l0;
        final ListNode<Integer> finalL1 = l1;
        ListNode<Integer> result =
                executor.run(() -> overlappingNoCycleLists(finalL0, finalL1));

        if (result != common) {
            throw new TestFailure("Invalid result");
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "DoTerminatedListsOverlap.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
