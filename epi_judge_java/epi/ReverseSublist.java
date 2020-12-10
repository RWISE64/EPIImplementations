package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class ReverseSublist {
    @EpiTest(testDataFile = "reverse_sublist.tsv")

    /*
    My solution might be a bit messy, requires some drawing to understand what it's doing
    Time: O(n) - just runs through the list once
    Space: O(1) - uses a constant amount of nodes
     */
    public static ListNode<Integer> reverseSublist(ListNode<Integer> L, int start,
                                                   int finish) {
        if (L == null || L.next == null)
            return L;
        ListNode<Integer> dummy = new ListNode(0, L), prev = dummy, iter = L, beforeRev = null, endRev = null;
        int c = 1;
        // Set up so iter = first node of sublist, prev = node before sublist
        while (c < start) {
            prev = prev.next;
            iter = iter.next;
            c++;
        }
        // beforeRev stores node before the sublist, so we can have it point to the reversed list later
        beforeRev = prev;
        // endRev will be the last node of the reversed sublist
        endRev = iter;
        // Use endRev.next to store where iter should go next, since iter.next will end up pointing to prev
        endRev.next = iter.next;
        while (c < finish) {
            // Move prev up
            prev = iter;
            // Set iter to the next node in the list, which is stored in endRev.next
            iter = endRev.next;
            // Set endRev to point to next node in list
            // This makes it so endRev ends up pointing to the nodes after the sublist
            endRev.next = iter.next;
            // Make iter point backwards (this is where the reversing occurs
            iter.next = prev;
            c++;
        }
        // Make beforeRev point to the new start of the sublist
        beforeRev.next = iter;
        return dummy.next;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ReverseSublist.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
