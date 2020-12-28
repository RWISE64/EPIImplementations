package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MajorityElement {

//    /*
//    Initial approach:
//    Use HashMap to store the number of occurrences of each string
//    Return whichever string occurs the most
//    Time: O(n)
//    Space: O(n). Want to try to do this with constant space (if possible)
//     */
//    public static String majoritySearch(Iterator<String> stream) {
//        HashMap<String, Integer> map = new HashMap<>();
//        int curMax = 0;
//        String majElement = "";
//        while (stream.hasNext()) {
//            String cur = stream.next();
//            int count = map.getOrDefault(cur, 0) + 1;
//            if (count > curMax) {
//                curMax = count;
//                majElement = cur;
//            }
//            map.put(cur, count);
//        }
//        return majElement;
//    }

    /*
    There's some way to mathematically prove this, but it's been a while since I've done discrete math
    I think we can essentially track one strings and two counts - the current majority string, the number
    of occurrences of that string, and the number of occurrences that aren't that string
    Whenever the second count passes the majority, we set the current string to the majority
    Essentially, the max element is bound to take the lead at some point
    Even if a different element takes the lead, then the differences between the two counts should still
    be possible to overcome by the majority element.
    Time: O(n)
    Space: O(1)
     */
    public static String majoritySearch(Iterator<String> stream) {
        String majElement = "";
        int curMax = 0, curMin = 0;
        while (stream.hasNext()) {
            String cur = stream.next();
            if (cur.equals(majElement))
                curMax++;
            else if (curMin == curMax) {
                curMax++;
                majElement = cur;
            }
            else {
                curMin++;
            }
        }
        return majElement;
    }

    @EpiTest(testDataFile = "majority_element.tsv")
    public static String majoritySearchWrapper(List<String> stream) {
        return majoritySearch(stream.iterator());
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "MajorityElement.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
