package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class PowerXY {
    @EpiTest(testDataFile = "power_x_y.tsv")
    /*
    Reduce work by splitting the exponent down and storing results
    Time: O(n), where n = most significant bit index of y
    Space: O(n), uses stack for storage on each recursive call
    Could reduce space to O(1) by using a while loop
     */
    public static double power(double x, int y) {
        if (y == 0)
            return 1;
        // Deal with negatives by calling power with the inverse of x and -y
        if (y < 0)
            return power(1 / x, -y);
        // Track whether we need to mult by one more x after
        boolean odd = ((y & 1) == 1);
        // Just dividing y by 2
        int halfExp = y >> 1;
        // Get (x ^ (y / 2))
        double halfPow = power(x, halfExp);
        // Return x ^ (y / 2) * x ^ (y / 2), mult by x if y was odd
        return halfPow * halfPow * (odd ? x : 1);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "PowerXY.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
