package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class Parity {
  /*
  Returns the parity of long x, where 1 = odd parity and 0 = even parity
  e.g. 13 = 0b1101 => return 1
   */
  // Implementation 2: O(n) where n = # of set bits in x
  @EpiTest(testDataFile = "parity.tsv")
  public static short parity(long x) {
    // Flag telling whether the currently read bits have odd parity
    boolean odd = false;
    // Loop until x = 0, since if all remaining bits are 0, the parity is unaffected by them
    while (x != 0) {
      // Flip parity each time, as loop should occur once for each 1 in x
      odd = !odd;
      // Bit manipulation, x&(x-1) removes the lowest set bit
      // e.g. x = 0b110, x - 1 = 0b101 => x&(x-1) = 0b100
      x = x & (x - 1);
    }
    // Return 1 if odd parity, 0 otherwise
    return (short) ((odd) ? 1 : 0);
  }

//  Implementation 1: O(n) where n = # of bits in x worstcase, O(1) bestcase (all 0s)
//  public static short parity(long x) {
//    // Since the algorithm needs to iterate over all bits of x at least once, I expect this to be at least O(n)
//    // Flag telling whether the currently read bits have odd parity
//    boolean odd = false;
//    // Loop until x = 0, since if all remaining bits are 0, the parity is unaffected by them
//    while (x != 0) {
//      // If leftmost bit == 1, swap parity
//      if ((x & 1) == 1)
//        odd = !odd;
//      // Shift right by one
//      x = x >> 1;
//    }
//    // Return 1 if odd parity, 0 otherwise
//    return (short) ((odd) ? 1 : 0);
//  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Parity.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
