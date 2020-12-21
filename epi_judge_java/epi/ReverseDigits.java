package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class ReverseDigits {
    /*
    Seems pretty straight-forward. Just perform modulus by 10 to get the lowest digit of x, and build
    the result in reverse based on that.
    Time: O(n) where n is the number of digits in x
    Space: O(1)
     */
    @EpiTest(testDataFile = "reverse_digits.tsv")
    public static long reverse(int x) {
        long res = 0;
        int rem = x;
        // Just continue operation until rem is reduced to 0
        while (rem != 0) {
            // Get smallest digit
            int cur = rem % 10;
            // Make space for digit
            res *= 10;
            // Add digit
            res += cur;
            // Remove digit
            rem /= 10;
        }
        return res;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ReverseDigits.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
