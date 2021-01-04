package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class OnlineMedian {
    /*
    Use two heaps to track each half of the numbers seen so far
    Keep the heaps semi-even so that we can figure out what the median is
    Time: O(logn), where n is the size of the sequence
    Space: O(n)
     */
    public static List<Double> onlineMedian(Iterator<Integer> sequence) {
        List<Double> res = new ArrayList<>();
        // MinHeap will track the smallest number in the half larger than the median
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((n1, n2) -> Integer.compare(n1, n2));
        // MaxHeap will track the largest number in the half smaller than the median
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((n1, n2) -> Integer.compare(n2, n1));
        while (sequence.hasNext()) {
            // Initial value, just read to straight to res and add to minHeap
            if (res.size() == 0) {
                minHeap.add(sequence.next());
                res.add((double) minHeap.peek());
            }
            else {
                double lastMedian = res.get(res.size() - 1);
                int next = sequence.next();
                // Add number to the correct half
                if (next >= lastMedian) {
                    minHeap.add(next);
                }
                else {
                    maxHeap.add(next);
                }
                // Balance the two if necessary (at most should be one apart)
                if (minHeap.size() > maxHeap.size() + 1)
                    maxHeap.add(minHeap.remove());
                else if (maxHeap.size() > minHeap.size() + 1)
                    minHeap.add(maxHeap.remove());
                // Figure out what to add to res
                if (maxHeap.size() > minHeap.size())
                    res.add((double) maxHeap.peek());
                else if (minHeap.size() > maxHeap.size())
                    res.add((double) minHeap.peek());
                // If sizes are even, return average of two middle points
                else
                    res.add((double) (minHeap.peek() + maxHeap.peek()) / 2);
            }
        }
        return res;
    }

    @EpiTest(testDataFile = "online_median.tsv")
    public static List<Double> onlineMedianWrapper(List<Integer> sequence) {
        return onlineMedian(sequence.iterator());
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "OnlineMedian.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
