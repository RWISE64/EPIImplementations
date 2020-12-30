package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.Collections;
import java.util.List;

public class SortedArrayRemoveDups {
    // Returns the number of valid entries after deletion.
    /*
    Use two iterators to complete without extra space
    One iterates directly through the array
    The other marks the point where the next non-dup element should go
    Time: O(n)
    Space: O(1)
     */
    public static int deleteDuplicates(List<Integer> A) {
        if (A.size() <= 0) {
            return 0;
        }
        int prev = A.get(0);
        int iter = 1;
        for (int i = iter; i < A.size(); i++) {
            int cur = A.get(i);
            if (cur != prev) {
                Collections.swap(A, iter++, i);
                prev = cur;
            }
        }
        return iter;
    }

    @EpiTest(testDataFile = "sorted_array_remove_dups.tsv")
    public static List<Integer> deleteDuplicatesWrapper(TimedExecutor executor,
                                                        List<Integer> A)
            throws Exception {
        int end = executor.run(() -> deleteDuplicates(A));
        return A.subList(0, end);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SortedArrayRemoveDups.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
