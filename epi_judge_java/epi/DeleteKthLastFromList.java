package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class DeleteKthLastFromList {
    @EpiTest(testDataFile = "delete_kth_last_from_list.tsv")

    // Assumes L has at least k nodes, deletes the k-th last node in L.
    /*
    Just used two iters, one k ahead of the other
    Iterates both until the k-ahead reaches the end
    Then deletes the node following the slower iter
    Time: O(n)
    Space: O(1)
     */
    public static ListNode<Integer> removeKthLast(ListNode<Integer> L, int k) {
        ListNode<Integer> dummyHead = new ListNode(0, L), kAhead = dummyHead, beforeKth = dummyHead;
        for (int i = 0; i < k; i++) {
            if (kAhead.next != null)
                kAhead = kAhead.next;
            else
                return dummyHead.next;
        }

        while (kAhead.next != null) {
            kAhead = kAhead.next;
            beforeKth = beforeKth.next;
        }
        beforeKth.next = beforeKth.next.next;
        return dummyHead.next;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "DeleteKthLastFromList.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
