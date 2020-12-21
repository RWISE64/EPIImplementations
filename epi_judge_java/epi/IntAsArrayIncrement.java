package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;

public class IntAsArrayIncrement {
    @EpiTest(testDataFile = "int_as_array_increment.tsv")
    /*
    Pretty straight-forward, just add starting at the ones and carry up if necessary
    Only catch is when the list is all 9's, in which case we need to do a slightly
    special operation to avoid shifting the entire array to the right by one
    Time: O(n)
    Space: O(1) (not including the original list)
     */
    public static List<Integer> plusOne(List<Integer> A) {
        for (int i = A.size() - 1; i >= 0; i--) {
            if (A.get(i) == 9) {
                // For the edge-case where the integer is all 9's
                // Rather than shifting all to add a one at the start,
                // just add a zero at the end. Saves a full iteration.
                if (i == 0) {
                    A.set(i, 1);
                    A.add(0);
                }
                else {
                    A.set(i, 0);
                }
            }
            else {
                A.set(i, A.get(i) + 1);
                // End if not a 9 (since it won't permeate upwards)
                break;
            }
        }
        return A;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IntAsArrayIncrement.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
