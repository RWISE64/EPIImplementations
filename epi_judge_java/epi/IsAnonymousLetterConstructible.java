package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;

public class IsAnonymousLetterConstructible {
    @EpiTest(testDataFile = "is_anonymous_letter_constructible.tsv")

    /*
    Initial Implementation
    Runtime: O(n + m), where n = letterText.length(), m = magazineText.length()
    Space: O(k), where k = different letters in letterText (store one mapping per unique letter)
     */
    public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                            String magazineText) {
        // Using one HashMap to first store letterText letter counts, then decrement for magazineText's letters
        // Essentially the map will store <letter, letters in letterText - letters in magazineText>
        // I'll remove the pair from the map when it reaches 0 so I can do an easy check at the end
        HashMap<Character, Integer> counts = new HashMap();
        // Add letters of letterText to counts
        for (int i = 0; i < letterText.length(); i++) {
            char cur = letterText.charAt(i);
            // Either initializes cur to 1 or increments if already present in counts
            counts.put(cur, counts.getOrDefault(cur, 0) + 1);
        }
        // Decrement counts based on magazineText, remove mapping when reaches 0
        for (int i = 0; i < magazineText.length(); i++) {
            char cur = magazineText.charAt(i);
            if (counts.containsKey(cur)) {
                // Remove if only one left
                if (counts.get(cur) == 1)
                    counts.remove(cur);
                // Otherwise just decrement
                else if (counts.get(cur) != null)
                    counts.put(cur, counts.get(cur) - 1);
                // Check whether counts is empty yet (if empty, anon letter can be formed)
                if (counts.isEmpty())
                    return true;
            }
        }
        return (counts.isEmpty());
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "IsAnonymousLetterConstructible.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
