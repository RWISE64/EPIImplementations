package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class LookAndSay {
    @EpiTest(testDataFile = "look_and_say.tsv")

    /*
    Pretty much just needed to iteratively build each string, resulting in a horrendous runtime
    Both time and space sorta depend on how long the string will get, which is pretty hard to put a finger on.
    Time: ???
    Space: ???
     */
    public static String lookAndSay(int n) {
        String res = "1";
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i < n; i++) {
            char curNum = res.charAt(0);
            int curCount = 1;
            for (int j = 1; j < res.length(); j++) {
                if (curNum == res.charAt(j))
                    curCount++;
                else {
                    temp.append(curCount);
                    temp.append(curNum);
                    curNum = res.charAt(j);
                    curCount = 1;
                }
            }
            temp.append(curCount);
            temp.append(curNum);
            res = temp.toString();
            temp = new StringBuilder();
        }
        return res;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LookAndSay.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
