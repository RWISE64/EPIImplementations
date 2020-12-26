package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class IsValidSudoku {
  @EpiTest(testDataFile = "is_valid_sudoku.tsv")

  // Check if a partially filled matrix has any conflicts.
  /*
  Solves using a length 9 array for each row, column, and square. Could be done without...
  Time: O(1), since it'll always iterate through the n x n partialAssignment once, but n always = 9
  Space: Also O(1) for the same reason, but we could again reduce it
   */
  public static boolean isValidSudoku(List<List<Integer>> partialAssignment) {
    int[][] rows = new int[9][9];
    int[][] cols = new int[9][9];
    int[][] squares = new int[9][9];
    for (int i = 0; i < partialAssignment.size(); i++) {
      for (int j = 0; j < partialAssignment.get(0).size(); j++) {
        int cur = partialAssignment.get(i).get(j);
        if (cur != 0) {
          if (rows[i][cur - 1] != 0 || cols[j][cur - 1] != 0 || squares[((j / 3) * 3) + i / 3][cur - 1] != 0)
            return false;
          rows[i][cur - 1] = 1;
          cols[j][cur - 1] = 1;
          squares[((j / 3) * 3) + i / 3][cur - 1] = 1;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsValidSudoku.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
