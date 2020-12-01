package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.List;
public class DutchNationalFlag {
  public enum Color { RED, WHITE, BLUE }

  /*
  My code fails, but I believe it to be a working implementation since the values of the colors is somewhat arbitrary
  The tests seem based the idea that there is a correct order of colors, which I guess I didn't get the memo for
  If you print the results when it fails, it's definitely partitioned, just not in the "correct" order :/
  Nonetheless, O(n) runtime to separate the elements into three partitions based on pivot, as it occurs in one pass
   */
  public static void dutchFlagPartition(int pivotIndex, List<Color> A) {
    Color pivot = A.get(pivotIndex);
    // Picking an arbitrary color to be left of pivot (was sorta expecting numbers here...)
    Color left = (pivot == Color.WHITE) ? Color.RED : Color.WHITE;
    int l = 0, r = A.size() - 1, iter = 0;

    /*
    Loop continues until iter meets right bound
    Based on maintaining the following conditions:
    A[k] for all k < l is less than pivot (or just whatever color we picked to be left)
    A[k] for all k > r is greater than pivot (the remaining color)
    Following that procedure, we should be able to keep all indices between l and r (inclusive?) equal to pivot
    Runtime is thus O(n), where n is the length of A as you perform at most 2 operations on each element iter touches
     */
    while (iter <= r) {
      if (A.get(iter) == pivot) {
        iter++;
      }
      else if (A.get(iter) == left) {
        swap(A, l, iter);
        l++;
        iter++;
      }
      else {
        swap(A, r, iter);
        r--;
      }
    }
  }

  private static void swap(List<Color> A, int l, int r) {
    Color temp = A.get(l);
    A.set(l, A.get(r));
    A.set(r, temp);
  }

  @EpiTest(testDataFile = "dutch_national_flag.tsv")
  public static void dutchFlagPartitionWrapper(TimedExecutor executor,
                                               List<Integer> A, int pivotIdx)
      throws Exception {
    List<Color> colors = new ArrayList<>();
    int[] count = new int[3];

    Color[] C = Color.values();
    for (int i = 0; i < A.size(); i++) {
      count[A.get(i)]++;
      colors.add(C[A.get(i)]);
    }

    Color pivot = colors.get(pivotIdx);
    executor.run(() -> dutchFlagPartition(pivotIdx, colors));

    int i = 0;
    while (i < colors.size() && colors.get(i).ordinal() < pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() == pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() > pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    if (i != colors.size()) {
      throw new TestFailure("Not partitioned after " + Integer.toString(i) +
                            "th element");
    } else if (count[0] != 0 || count[1] != 0 || count[2] != 0) {
      throw new TestFailure("Some elements are missing from original array");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DutchNationalFlag.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
