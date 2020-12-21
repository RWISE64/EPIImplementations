package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class StringTransformability {
    /*
    EPI solution is definitely the least complicated, so that's what I'll be implementing
    Thought about doing more pre-processing on the set to create a fancy hashmap
    In the end, pre-processing will likely take up more time and space, so here we are
    Pretty much does a BFS by varying the current string by one letter then visiting that string (if it exists)
    Time: O(D * s.length()). Apparently should be O(D^2) due to BFS, but I believe my way avoids nodes
    being visited multiple times => O(D * s.length()) (I think)
    Space: O(D), just store one in HashMap per
     */
    @EpiTest(testDataFile = "string_transformability.tsv")
    public static int transformString(Set<String> D, String s, String t) {
        // Using a HashMap to store the distance from s each String is
        // If distance = 0, the string hasn't been visited. s won't be included in the map.
        HashMap<String, Integer> distances = new HashMap();
        for (String cur : D) {
            if (!distances.containsKey(cur))
                distances.put(cur, 0);
        }
        Queue<String> q = new ArrayDeque();
        q.add(s);
        while (!q.isEmpty()) {
            String cur = q.poll();
            int distance = distances.remove(cur);
            for (int i = 0; i < cur.length(); i++) {
                for (int j = 0; j < 26; j++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append((i == 0) ? "" : cur.substring(0, i));
                    sb.append((char) ('a' + j));
                    sb.append((i == cur.length() - 1) ? "" : cur.substring(i + 1));
                    // Unvisited strings should have a distance of 0
                    String var = sb.toString();
                    if (distances.containsKey(var) && distances.get(var) == 0) {
                        if (var.equals(t))
                            return distance + 1;
                        q.add(var);
                        distances.put(var, distance + 1);
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "StringTransformability.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
