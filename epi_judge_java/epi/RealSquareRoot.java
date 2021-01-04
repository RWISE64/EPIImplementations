package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class RealSquareRoot {
    @EpiTest(testDataFile = "real_square_root.tsv")

    public static double squareRoot(double x) {
        double l, r;
        if (x < 1) {
            l = x;
            r = 1.0;
        }
        else {
            l = 1.0;
            r = x;
        }

        while (compare(l, r) != 0) {
            double m = l + (r - l) / 2;
            // Case where m * m is too large
            if (compare(m * m, x) == 1)
                r = m;
            else
                l = m;
        }
        return l;
    }
    // this precision stuff is new to me, so it's gonna be a bit loose
    private static int compare(double a, double b) {
        final double EPSILON = 0.0000001;
        // Use normalization?
        double diff = (a - b) / Math.max(Math.abs(a), Math.abs(b));
        // Return -1 if less than EPSILON, 1 if more, and 0 if within our tolerance
        return (diff < -EPSILON) ? -1 : ((diff > EPSILON) ? 1 : 0);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "RealSquareRoot.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
