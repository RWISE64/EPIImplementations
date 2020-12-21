package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class IsStringPalindromicPunctuation {
    @EpiTest(testDataFile = "is_string_palindromic_punctuation.tsv")

    /*
    Just use two pointers, and an additional function to avoid non-alphanumeric characters
    Time: O(n), where n is the length of s
    Space: O(n), since I create a lowercase version of s
     */
    public static boolean isPalindrome(String s) {
        // Since we ignore capitalization, just convert s to lowercase to begin with
        // Could have avoided by just applying the lowercase during the actual comparison
        String lower = s.toLowerCase();
        // Use two pointers, left and right to compare each side
        int l = 0, r = s.length() - 1;
        // Don't check l == r since they are guaranteed to be the same
        while (l < r) {
            char lc = lower.charAt(l), rc = lower.charAt(r);
            // Want to advance l and r until they are both alphanumeric
            if (!isAlphanumeric(lc)) {
                l++;
            }
            else if (!isAlphanumeric(rc)) {
                r--;
            }
            // Compare lc and rc, iterate both if they are equal
            else {
                if (lc != rc)
                    return false;
                l++;
                r--;
            }
        }
        return true;
    }

    // Just returns whether the char is a letter or digit
    // Turns out isLetterOrDigit is a thing, so whoops.
    private static boolean isAlphanumeric(char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsStringPalindromicPunctuation.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
