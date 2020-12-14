package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.List;

public class ReplaceAndRemove {

    /*
    Do two passes, one normally then one backwards
    First pass removes b, counts the boundaries of our new areas, and shifts the remaining left
    Second pass fills from the back, which should work since we shifted everything left
    Time: O(n) - just two passes
    Space: O(1)
     */
    public static int replaceAndRemove(int size, char[] s) {
        // Dumb edge case catch
        if (size == 0)
            return 0;
        int resEntryCount = 0;
        int newSize = 0;
        // First Pass: remove b's, shift remaining left, count how long our sequence will be
        for (int i = 0, j = 0; i < size; i++) {
            char c = s[i];
            // a => 2 d's, so add two
            if (c == 'a')
                resEntryCount += 2;
            // All letters other than b should be kept
            else if (c != 'b')
                resEntryCount++;
            // Skip b's, shove good letters to wherever j is
            if (c != 'b') {
                s[j] = c;
                // Store newSize, so we know where to start on our second pass
                newSize = j++;
            }
        }

        int iter = resEntryCount - 1;
        // Last Pass: fill from back, ignoring b's
        for (int i = newSize; i >= 0; i--) {
            char c = s[i];
            // Add two d's to the end for a
            if (c == 'a') {
                s[iter--] = 'd';
                s[iter--] = 'd';
            }
            else if (c != 'b')
                s[iter--] = c;
        }
        return resEntryCount;
    }

    @EpiTest(testDataFile = "replace_and_remove.tsv")
    public static List<String>
    replaceAndRemoveWrapper(TimedExecutor executor, Integer size, List<String> s)
            throws Exception {
        char[] sCopy = new char[s.size()];
        for (int i = 0; i < size; ++i) {
            if (!s.get(i).isEmpty()) {
                sCopy[i] = s.get(i).charAt(0);
            }
        }

        Integer resSize = executor.run(() -> replaceAndRemove(size, sCopy));

        List<String> result = new ArrayList<>();
        for (int i = 0; i < resSize; ++i) {
            result.add(Character.toString(sCopy[i]));
        }
        return result;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ReplaceAndRemove.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
