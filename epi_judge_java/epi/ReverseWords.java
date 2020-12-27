package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;

public class ReverseWords {

//    /*
//    Semi-Brute force implementation: store the words, then
//    modify input
//    Time: O(n)
//    Space: ~O(n), since the chars stored should be slightly less than the length of input
//    In next implementation, want to reduce the space to O(1)
//     */
//    public static void reverseWords(char[] input) {
//        StringBuilder cur = new StringBuilder();
//        ArrayList<String> words = new ArrayList<>();
//        for (char c : input) {
//            if (c == ' ') {
//                words.add(cur.toString());
//                cur = new StringBuilder();
//            }
//            else {
//                cur.append(c);
//            }
//        }
//        words.add(cur.toString());
//
//        for (int i = words.size() - 1, j = 0; i >= 0; i--) {
//            String word = words.get(i);
//            for (int k = 0; k < word.length(); k++, j++) {
//                input[j] = word.charAt(k);
//            }
//            if (j < input.length)
//                input[j++] = ' ';
//        }
//    }

    /*
    Sorta stumbled upon the solution through experimentation
    Just need to reverse each individual word
    After that, it's easy to just swap the front and back chars
    Kinda a scuffed implementation still...
    Time: O(n)
    Space: O(1)
     */
    public static void reverseWords(char[] input) {
        for (int i = -1, j = 0; i < input.length; i++) {
            // Start the reversal loop if at the end of a word
            if (i == input.length - 1 || input[i + 1] == ' ') {
                int end = i;
                while (end > j) {
                    char temp = input[j];
                    input[j++] = input[end];
                    input[end--] = temp;
                }
                // Slight adjustment, since now I want to loop to skip past extra spaces
                do {
                    i++;
                } while (i < input.length - 1 && input[i + 1] == ' ');
                // Set j to be the start of the next word
                j = i + 1;
            }
        }

        // Swap the front and back chars moving towards the center
        for (int l = 0, r = input.length - 1; l < r; l++, r--) {
            char temp = input[l];
            input[l] = input[r];
            input[r] = temp;
        }
    }

    @EpiTest(testDataFile = "reverse_words.tsv")
    public static String reverseWordsWrapper(TimedExecutor executor, String s)
            throws Exception {
        char[] sCopy = s.toCharArray();

        executor.run(() -> reverseWords(sCopy));

        return String.valueOf(sCopy);
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ReverseWords.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
