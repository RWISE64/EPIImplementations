package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

public class EvenOddListMerge {
    @EpiTest(testDataFile = "even_odd_list_merge.tsv")

    /*
    Just use some temporary nodes to store two separate lists, then combine
    Time: O(n)
    Space: O(1) - since we are just storing a couple of references
     */
    public static ListNode<Integer> evenOddMerge(ListNode<Integer> L) {
        // Use separate lists to split up the starting list - use dummy nodes
        // Just using two lists to reduce the complexity
        ListNode<Integer> even = new ListNode(0, null), odd = new ListNode(0, null);
        ListNode<Integer> evenHead = even, oddHead = odd;
        ListNode<Integer> iter = L;
        // Tracks what to categorize the next node as
        boolean isEven = true;
        while (iter != null) {
            if (isEven) {
                even.next = iter;
                even = even.next;
            }
            else {
                odd.next = iter;
                odd = odd.next;
            }
            isEven = !isEven;
            iter = iter.next;
        }
        // Make sure we don't have any accidental circular references
        odd.next = null;
        even.next = oddHead.next;
        return evenHead.next;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "EvenOddListMerge.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
