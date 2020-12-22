package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class SortAlmostSortedArray {

    /*
    Use a minheap to utilize the fact that numbers are at most k away
    Time: O(nlogk), where n is the length of the sequence
    Space: O(k) for the heap, O(n) if you consider the result list as well
     */
    public static List<Integer>
    sortApproximatelySortedData(Iterator<Integer> sequence, int k) {
        // Since each number is at most k away, the number at res[0] can't be after res[2]
        // We can use a heap to find the min of res[0] - res[2]
        // This allows us to actually utilize the near-sorted nature of the sequence
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k, (x, y) -> x - y);
        List<Integer> res = new ArrayList<>();
        while (sequence.hasNext()) {
            int cur = sequence.next();
            // Initially need to add until the minHeap is size k
            if (minHeap.size() < k) {
                minHeap.add(cur);
            }
            // Avoid inserting if the cur is smaller than the min in the heap
            // Helps avoid a redundant log(k) insert and remove
            else if (cur < minHeap.peek()) {
                res.add(cur);
            }
            else {
                res.add(minHeap.remove());
                minHeap.add(cur);
            }
        }
        // Need to empty out the minHeap
        while (!minHeap.isEmpty()) {
            res.add(minHeap.remove());
        }
        return res;
    }

    @EpiTest(testDataFile = "sort_almost_sorted_array.tsv")
    public static List<Integer>
    sortApproximatelySortedDataWrapper(List<Integer> sequence, int k) {
        return sortApproximatelySortedData(sequence.iterator(), k);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SortAlmostSortedArray.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
