package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class ConvertBase {
    @EpiTest(testDataFile = "convert_base.tsv")

    /*
    This is a truly garbage can solution - I'm not a fan of these problems that seem to involve knowing ascii codes
     */
    public static String convertBase(String numAsString, int b1, int b2) {
        int pow = 1;
        int total = 0;
        boolean neg = false;
        for (int i = numAsString.length() - 1; i >= 0; i--) {
            if (numAsString.charAt(i) == '-')
                neg = true;
            else {
                int val = (numAsString.charAt(i) - '0');
                if (val > 10)
                    val = val - ('A' - '9' - 1);
                total += val * pow;
                pow *= b1;
            }
        }
        if (total == 0)
            return "0";
        StringBuilder sb = new StringBuilder();
        while (total > 0) {
            int val = total % b2;
            total /= b2;
            sb.append(getChar(val));
        }
        if (neg)
            sb.append('-');
        return sb.reverse().toString();
    }

    static char getChar(int val) {
        if (val < 10)
            return (char) (val + '0');
        else
            return (char) (val - 10 + 'A');
    }


    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ConvertBase.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
