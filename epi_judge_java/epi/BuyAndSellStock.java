package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class BuyAndSellStock {
  @EpiTest(testDataFile = "buy_and_sell_stock.tsv")
  /*
  This solution utilizes the fact that the buy always needs to take place before the sell
  Due to this, can just track minimum as you iterate through, and continually compare with next encountered price
  Time: O(n), where n = prices.size() (just one iteration through prices)
  Space: O(1) (constant amount of variables, not dependent on size of prices)
   */
  public static double computeMaxProfit(List<Double> prices) {
    // Tracks to lowest encountered price during a left to right traversal
    int curMinInd = 0;
    double curMaxProf = 0;
    for (int i = 0; i < prices.size(); i++) {
      // Update current min if necessary
      if (prices.get(i) < prices.get(curMinInd))
        curMinInd = i;
      // Keep maximum encountered profit
      curMaxProf = Math.max(prices.get(i) - prices.get(curMinInd), curMaxProf);
    }
    return curMaxProf;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStock.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
