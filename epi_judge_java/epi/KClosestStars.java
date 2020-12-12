package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.EpiTestExpectedType;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.*;
import java.util.function.BiPredicate;

public class KClosestStars {
    @EpiUserType(ctorParams = {double.class, double.class, double.class})

    public static class Star implements Comparable<Star> {
        private double x, y, z;

        public Star(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double distance() {
            return Math.sqrt(x * x + y * y + z * z);
        }

        @Override
        public int compareTo(Star that) {
            return Double.compare(this.distance(), that.distance());
        }

        @Override
        public String toString() {
            return String.valueOf(distance());
        }
    }

    /*
    Just used a maxHeap so that the largest value is always removed first, leaving us with the k smallest values
    Time: O(nlogn), where n = number of elements. This is the case since insertions take logn time with heaps
    Space: O(k), as heap maxes out at size k (or k + 1 for a moment)
     */
    public static List<Star> findClosestKStars(Iterator<Star> stars, int k) {
        // I know it was implemented with comparable, but I wanted to actually write the comparison myself
        // Should place the larger distance first
        PriorityQueue<Star> maxHeap = new PriorityQueue<Star>(k, (s1, s2) -> Double.compare(s2.distance(), s1.distance()));
        while(stars.hasNext()) {
            maxHeap.add(stars.next());
            // Remove top if heap is larger than k. This should remove the furthest away star of the heap
            if (maxHeap.size() > k)
                maxHeap.poll();
        }
        return new ArrayList(maxHeap);
    }

    @EpiTest(testDataFile = "k_closest_stars.tsv")
    public static List<Star> findClosestKStarsWrapper(List<Star> stars, int k) {
        return findClosestKStars(stars.iterator(), k);
    }

    @EpiTestExpectedType
    public static List<Double> expectedType;

    @EpiTestComparator
    public static boolean comp(List<Double> expected, List<Star> result) {
        if (expected.size() != result.size()) {
            return false;
        }
        Collections.sort(result);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).distance() != expected.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "KClosestStars.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
