package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class NumberOfTraversalsMatrix {
  @EpiTest(testDataFile = "number_of_traversals_matrix.tsv")

  /*
  Use dp[n + 1][m] + dp[n][m + 1] to calculate paths from dp[n][m]
  Time: O(nm)
  Space: O(nm)
   */
  public static int numberOfWays(int n, int m) {
    return getNumWays(n, m, new int[n][m]);
  }

  private static int getNumWays(int n, int m, int[][] dp) {
    if (dp[n - 1][m - 1] == 0) {
      if (n <= 1 || m <= 1) {
        dp[n - 1][m - 1] = 1;
      } else {
        dp[n - 1][m - 1] = getNumWays(n - 1, m, dp) + getNumWays(n, m - 1, dp);
      }
    }
    return dp[n - 1][m - 1];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfTraversalsMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
