package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.HashSet;

public class IsListCyclic {

    /*
    Approach 2: One fast, one slow iterator
    Have one iterator advance by one, another advance by two
    There is a cycle if they meet
    Need to then calculate the length of the cycle, then find the cycle by using to iterators a cycle length apart
    For the record, I managed to figure out the two different speed iterator approach, but could not find the way to
    find the starting node
    Time: we'll just say O(n). It'll just have to do an iteration or two of the list
    Space: O(1)
     */
    public static ListNode<Integer> hasCycle(ListNode<Integer> head) {
        // Two different speed iters
        ListNode<Integer> slow = head, fast = head;
        do {
            // If fast ever reaches null, there is no cycle present.
            if (fast.next == null || fast.next.next == null)
                return null;
            fast = fast.next.next;
            slow = slow.next;
        } while (fast != slow);

        // Will use fast iterator to figure out the length
        // Just iterate fast until it returns to slow, keeping count along the way
        int length = 0;
        do {
            length++;
            fast = fast.next;
        } while (fast != slow);

        // Set both nodes back to head, advance fast length times
        slow = head;
        fast = head;
        for (int i = 0; i < length; i++) {
            fast = fast.next;
        }

        // Lastly, advance both fast and slow by one until they meet, then return slow (or fast)
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

//    /*
//    Approach 1: Sacrifice storage for runtime
//    Just use a hashset to store references, check whether reference has been seen each time
//    If no cycle, we should eventually just reach null
//    Time: O(n), where n is the number of nodes
//    Space: O(n), since worst case last node references itself
//     */
//    public static ListNode<Integer> hasCycle(ListNode<Integer> head) {
//        // Store visited nodes
//        HashSet<ListNode<Integer>> visited = new HashSet();
//        ListNode<Integer> cur = head;
//        while (cur.next != null) {
//            // If node is in the hashset, then it is the start of a cycle
//            if (visited.contains(cur))
//                return cur;
//            visited.add(cur);
//            cur = cur.next;
//        }
//        return null;
//    }

    @EpiTest(testDataFile = "is_list_cyclic.tsv")
    public static void HasCycleWrapper(TimedExecutor executor,
                                       ListNode<Integer> head, int cycleIdx)
            throws Exception {
        int cycleLength = 0;
        if (cycleIdx != -1) {
            if (head == null) {
                throw new RuntimeException("Can't cycle empty list");
            }
            ListNode<Integer> cycleStart = null, cursor = head;
            while (cursor.next != null) {
                if (cursor.data == cycleIdx) {
                    cycleStart = cursor;
                }
                cursor = cursor.next;
                if (cycleStart != null) {
                    cycleLength++;
                }
            }
            if (cursor.data == cycleIdx) {
                cycleStart = cursor;
            }
            if (cycleStart == null) {
                throw new RuntimeException("Can't find a cycle start");
            }
            cursor.next = cycleStart;
            cycleLength++;
        }

        ListNode<Integer> result = executor.run(() -> hasCycle(head));

        if (cycleIdx == -1) {
            if (result != null) {
                throw new TestFailure("Found a non-existing cycle");
            }
        } else {
            if (result == null) {
                throw new TestFailure("Existing cycle was not found");
            }

            ListNode<Integer> cursor = result;
            do {
                cursor = cursor.next;
                cycleLength--;
                if (cursor == null || cycleLength < 0) {
                    throw new TestFailure(
                            "Returned node does not belong to the cycle or is not the closest node to the head");
                }
            } while (cursor != result);

            if (cycleLength != 0) {
                throw new TestFailure(
                        "Returned node does not belong to the cycle or is not the closest node to the head");
            }
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsListCyclic.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
