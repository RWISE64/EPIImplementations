package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;

public class NumberOfScoreCombinations {
    @EpiTest(testDataFile = "number_of_score_combinations.tsv")

    /*
    Dynamic programming makes my head hurt
    Just calculate the scores possible with just one score, then two, then three, etc.
    Use previously calculated values from array rather than recalculating
    Runtime: O(n*m), where n = finalScore, and m = individualPlayScores.size()
    Space: O(n*m)
     */
    public static int
    numCombinationsForFinalScore(int finalScore,
                                 List<Integer> individualPlayScores) {
        // No guarantee that the scores are sorted
        Collections.sort(individualPlayScores);
        int[][] combos = new int[individualPlayScores.size()][finalScore + 1];
        for (int i = 0; i < individualPlayScores.size(); i++) {
            // Only one way to make zero, no matter how many values are added
            combos[i][0] = 1;
            int score = individualPlayScores.get(i);
            for (int j = 1; j <= finalScore; j++) {
                int total = 0;
                // Add old plays (don't include the new value)
                if (i > 0)
                    total += combos[i - 1][j];
                // Add new play
                if (score <= j)
                    total += combos[i][j - score];
                combos[i][j] = total;
            }
        }
        return combos[individualPlayScores.size() - 1][finalScore];
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "NumberOfScoreCombinations.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
