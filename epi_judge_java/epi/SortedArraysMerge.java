package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SortedArraysMerge {
    @EpiTest(testDataFile = "sorted_arrays_merge.tsv")

    /*
    Just uses a minHeap based on the first element of each list
    Time Complexity: O(nlogk), where n = total number of elements, k = number of lists
    The heap should have an insert time of logk, as there should only be k elements
    Space Complexity: O(k), as the heap only stores references to k lists
    Could be O(n) complexity if you only consdier the final list.
     */
    public static List<Integer>
    mergeSortedArrays(List<List<Integer>> sortedArrays) {
        // Min heap based on first value of each list
        PriorityQueue<List<Integer>> listHeap = new PriorityQueue<>((l1, l2) -> l1.get(0) - l2.get(0));
        // Add all lists to the heap
        for (List<Integer> list : sortedArrays)
            listHeap.add(list);
        List<Integer> merged = new ArrayList<>();
        // Loop until heap is empty
        // Remove min list, add first element to final list, then place list back in heap w/ first element removed
        while (!listHeap.isEmpty()) {
            List<Integer> minList = listHeap.poll();
            merged.add(minList.remove(0));
            if (!minList.isEmpty())
                listHeap.add(minList);
        }
        return merged;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SortedArraysMerge.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
