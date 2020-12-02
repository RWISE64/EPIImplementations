package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

public class StringIntegerInterconversion {

    public static String intToString(int x) {
        StringBuilder sb = new StringBuilder();
        boolean neg = false;
        if (x < 0)
            neg = true;
        do {
            sb.append((char) ('0' + Math.abs(x % 10)));
            x /= 10;
        } while (x != 0);
        if (neg)
            sb.append('-');
        return sb.reverse().toString();
    }

    // Trying out the book version since it uses many new functions to me
    public static int stringToInt(String s) {
        // Multiply by -1 if negative
        return (s.charAt(0) == '-' ? -1 : 1) *
                // Skip negative or positive (if present)
                s.substring((s.charAt(0) == '-' || s.charAt(0) == '+') ? 1 : 0)
                        // Get char stream
                        .chars()
                        // Perform operation on each value, shifting sum over 1 and adding the new value
                        .reduce(0, (sum, c) -> sum * 10 + c - '0');
    }

    //  My initial version
//  public static int stringToInt(String s) {
//    int res = 0;
//    boolean neg = false;
//    for (int i = 0; i < s.length(); i++) {
//      res *= 10;
//      if (s.charAt(i) == '-')
//        neg = true;
//      else if (s.charAt(i) != '+')
//        res += s.charAt(i) - '0';
//    }
//    return res * ((neg) ? -1 : 1);
//  }
    @EpiTest(testDataFile = "string_integer_interconversion.tsv")
    public static void wrapper(int x, String s) throws TestFailure {
        if (Integer.parseInt(intToString(x)) != x) {
            throw new TestFailure("Int to string conversion failed");
        }
        if (stringToInt(s) != x) {
            throw new TestFailure("String to int conversion failed");
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "StringIntegerInterconversion.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
