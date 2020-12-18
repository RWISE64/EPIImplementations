package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.List;

public class NearestRepeatedEntries {
    @EpiTest(testDataFile = "nearest_repeated_entries.tsv")

    /*
    Just uses a hashtable to keep track of the position of each occurrence
    Time: O(n) - just one iteration
    Space: O(k), where k = number of distinct words in paragraph
     */
    public static int findNearestRepetition(List<String> paragraph) {
        // Stores the shortest distance between repetitions
        int min = Integer.MAX_VALUE;
        // HashTable to track where a word occurs
        // Will contain the most recent occurrence of a word (since earlier would be further apart)
        HashMap<String, Integer> map = new HashMap();
        for (int i = 0; i < paragraph.size(); i++) {
            String cur = paragraph.get(i);
            if (map.containsKey(cur)) {
                // Set min to be distance between cur and last occurrence (if smaller)
                min = Math.min(min, i - map.get(cur));
            }
            map.put(cur, i);
        }
        // Return -1 if there are no repeats
        return (min == Integer.MAX_VALUE) ? -1 : min;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "NearestRepeatedEntries.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
