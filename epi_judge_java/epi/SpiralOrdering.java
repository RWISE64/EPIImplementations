package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;

public class SpiralOrdering {
    @EpiTest(testDataFile = "spiral_ordering.tsv")

    /*
    Pretty straight-forward. Just needed to be a bit smarter with how you iterate to avoid confusing iteration lengths
    Time: O(n^2)
    Space: O(n). Could have been avoided through multiple loops, but I didn't want to write four different loops...
     */
    public static List<Integer>
    matrixInSpiralOrder(List<List<Integer>> squareMatrix) {
        List<Integer> spiral = new ArrayList();
        int size = squareMatrix.size();
        for (int i = 0; i < size / 2; i++) {
            List<Integer> top = new ArrayList(), right = new ArrayList(),
                    bot = new ArrayList(), left = new ArrayList();
            // For each row, add spots 0 through n - 2
            // Ignoring the last spot allows all for rows to be the same size
            // Just helps avoid shenanigans due to different row/col lengths
            for (int j = i; j < size - i - 1; j++) {
                top.add(squareMatrix.get(i).get(j));
                right.add(squareMatrix.get(j).get(size - i - 1));
                bot.add(squareMatrix.get(size - i - 1).get(size - j - 1));
                left.add(squareMatrix.get(size - j - 1).get(i));
            }
            // Add all the separate rows
            // Inefficient compared to just adding them straight, but I can avoid multiple loops with this
            spiral.addAll(top);
            spiral.addAll(right);
            spiral.addAll(bot);
            spiral.addAll(left);
        }
        // If odd size, need to add the single square in the middle
        if (size % 2 == 1)
            spiral.add(squareMatrix.get(size / 2).get(size / 2));
        return spiral;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SpiralOrdering.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
