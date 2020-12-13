package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;
import epi.test_framework.LexicographicalListComparator;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class NQueens {
    @EpiTest(testDataFile = "n_queens.tsv")

    /*
    I don't even want to think about this monstrosity's runtime...
    I used a LinkedHashSet so that I can ensure now previous columns conflict in O(1) time
    This function pretty much tries to build the set from the ground up, splitting off for each possible solution
     */
    public static List<List<Integer>> nQueens(int n) {
        List<List<Integer>> positions = new ArrayList();
        nQueensRec(n, new LinkedHashSet<Integer>(), positions);
        return positions;
    }

    private static void nQueensRec(int n, LinkedHashSet<Integer> config, List<List<Integer>> positions) {
        // If config is has n items, we have a valid configuration
        if (config.size() == n) {
            positions.add(new ArrayList(config));
            return;
        }
        for (int i = 0; i < n; i++) {
            // Check that column i hasn't been occupied
            if (!config.contains(i)) {
                config.add(i);
                // Check that the diagonals are valid before continuing
                if (validDiagonals(config, n))
                    nQueensRec(n, config, positions);
                config.remove(i);
            }
        }
    }

    /*
    Just checks that the diagonals are valid
    Could have just checked that the last queen is diagonally valid, but that'd still involve comparing with every position
    Would have saved space within this function I guess
     */
    private static boolean validDiagonals(LinkedHashSet<Integer> config, int n) {
        HashSet<Integer> leftToRightDiagonals = new HashSet();
        HashSet<Integer> rightToLeftDiagonals = new HashSet();
        int row = 0;
        Iterator<Integer> iter = config.iterator();
        while (iter.hasNext()) {
            int val = iter.next();
            if (!leftToRightDiagonals.add(val - row) || !rightToLeftDiagonals.add((n - 1 - val) - row))
                return false;
            row++;
        }
        return true;
    }
//    /*
//    I thought there was actually a pattern... I'm leaving this because I thought it was cool
//    You could just add a queen in each corner of the previous solution to get new configurations
//    Turns out there are way more configurations than that, so my cool trick is for nothing
//     */
//    public static List<List<Integer>> nQueens(int n) {
//        List<List<Integer>> positions = new ArrayList();
//        if (n == 2 || n == 3)
//            return positions;
//        else if (n == 1) {
//            positions.add(new ArrayList(Arrays.asList(0)));
//            return positions;
//        }
//        int count = 4;
//        positions.add(new ArrayList(Arrays.asList(1, 3, 0, 2)));
//        positions.add(new ArrayList(Arrays.asList(2, 0, 3, 1)));
//        while (count++ < n) {
//            List<List<Integer>> newPositions = new ArrayList();
//            // Each time we add 1 row, we can put the queen in any of the corners
//            // Adding to the top = add new value to start of list
//            // Adding to the bot = add to end of list
//            // Adding to the left = add 1 to all previous values, val = 0
//            // Adding to the right = keep previous values, val = n - 1
//            for (List<Integer> config : positions) {
//                // Create list with one added to previous values
//                List<Integer> left = config.stream().map(v -> v + 1).collect(Collectors.toList());
//                List<Integer> topLeft = new ArrayList(left);
//                topLeft.add(0, 0);
//                List<Integer> botLeft = new ArrayList(left);
//                botLeft.add(n - 1, 0);
//                List<Integer> topRight = new ArrayList(config);
//                topRight.add(0,n - 1);
//                List<Integer> botRight = new ArrayList(config);
//                botRight.add(n - 1, n - 1);
//                newPositions.addAll(Arrays.asList(topLeft, botLeft, topRight, botRight));
//            }
//            positions = newPositions;
//        }
//        return positions;
//    }

    @EpiTestComparator
    public static boolean comp(List<List<Integer>> expected,
                               List<List<Integer>> result) {
        if (result == null) {
            return false;
        }
        expected.sort(new LexicographicalListComparator<>());
        result.sort(new LexicographicalListComparator<>());
        return expected.equals(result);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "NQueens.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
