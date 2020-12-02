package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SortedListsMerge {
  @EpiTest(testDataFile = "sorted_lists_merge.tsv")
  //@include
  // Solution using dummyHead as suggested by EPI, ridiculously more concise...
    public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1,
                                                      ListNode<Integer> L2) {
    ListNode<Integer> dummyHead = new ListNode(0, null), iter = dummyHead;
    while (L1 != null && L2 != null) {
      if (L1.data <= L2.data) {
        iter.next = L1;
        L1 = L1.next;
      }
      else {
        iter.next = L2;
        L2 = L2.next;
      }
      iter = iter.next;
    }
    if (L1 != null)
      iter.next = L1;
    else
      iter.next = L2;
    return dummyHead.next;
  }

//  Initial Solution: no dummyHead
//  public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1,
//                                                      ListNode<Integer> L2) {
//    ListNode<Integer> head = null, iter = null;
//    if (L1 == null && L2 == null)
//      return null;
//    if (L1 == null)
//      return L2;
//    if (L2 == null)
//      return L1;
//    if (L1.data <= L2.data) {
//      head = L1;
//      iter = L1;
//      L1 = L1.next;
//    }
//    else {
//      head = L2;
//      iter = L2;
//      L2 = L2.next;
//    }
//    while (L1 != null && L2 != null) {
//      if (L1.data <= L2.data) {
//        iter.next = L1;
//        L1 = L1.next;
//      }
//      else {
//        iter.next = L2;
//        L2 = L2.next;
//      }
//      iter = iter.next;
//    }
//    if (L1 != null)
//      iter.next = L1;
//    else
//      iter.next = L2;
//    return head;
//  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedListsMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
