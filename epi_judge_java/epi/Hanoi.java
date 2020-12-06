package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.*;

public class Hanoi {

    private static final int NUM_PEGS = 3;

    /*
    This problem made my brain sad.
    Time Complexity: O(2^n), as the length of the move list is always 2^n - 1
    The previous calculations all take less time than the final, so it just compounds to 2^n
    Space Complexity: O(2^n), again due to list length. Last list overrides the call stack and other storage
     */
    public static List<List<Integer>> computeTowerHanoi(int numRings) {
        List<List<Integer>> res = new ArrayList();
        // Base case, just move ring 1 from 0 to 1
        if (numRings == 1) {
            res.add(new ArrayList(Arrays.asList(0, 1)));
            return res;
        }
        // Get previous moves
        List<List<Integer>> prev = computeTowerHanoi(numRings - 1);
        // My solution isn't exactly based on understanding how the moves work, rather just a pattern I saw in the steps
        // Essentially, the previous list is always incorporated into the next, with new moves between that represent ring 1
        // Ring one needs to constantly move in a pattern, as it can't have anything placed on top
        // When numRings is even, ring 1 will move by 2 each time (loop back to 0, so 0->2, 2->1, 1->0, etc.)
        // When numRings is odd, ring 1 will move by 1 each time (0->1, 1->2, 2->1, etc.)
        // So, my solution just inserts the moves between each step
        int pos1 = 0;
        boolean odd = numRings % 2 == 1;
        for (List<Integer> move : prev) {
            // Calculate and add next move
            int pos1Next = (pos1 + ((odd) ? 1 : 2)) % NUM_PEGS;
            res.add(Arrays.asList(pos1, pos1Next));
            pos1 = pos1Next;
            // Add a move from last list
            res.add(move);
        }
        // Add one more to cap it (new moves surround old, e.g. if prev = [[0,1]], add [0,2] before and [2,1] after
        int pos1Next = (pos1 + ((odd) ? 1 : 2)) % NUM_PEGS;
        res.add(Arrays.asList(pos1, pos1Next));
        return res;
    }

    @EpiTest(testDataFile = "hanoi.tsv")
    public static void computeTowerHanoiWrapper(TimedExecutor executor,
                                                int numRings) throws Exception {
        List<Deque<Integer>> pegs = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            pegs.add(new LinkedList<>());
        }
        for (int i = numRings; i >= 1; --i) {
            pegs.get(0).addFirst(i);
        }

        List<List<Integer>> result =
                executor.run(() -> computeTowerHanoi(numRings));

        for (List<Integer> operation : result) {
            int fromPeg = operation.get(0);
            int toPeg = operation.get(1);
            if (!pegs.get(toPeg).isEmpty() &&
                    pegs.get(fromPeg).getFirst() >= pegs.get(toPeg).getFirst()) {
                throw new TestFailure("Illegal move from " +
                        String.valueOf(pegs.get(fromPeg).getFirst()) +
                        " to " +
                        String.valueOf(pegs.get(toPeg).getFirst()));
            }
            pegs.get(toPeg).addFirst(pegs.get(fromPeg).removeFirst());
        }

        List<Deque<Integer>> expectedPegs1 = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            expectedPegs1.add(new LinkedList<Integer>());
        }
        for (int i = numRings; i >= 1; --i) {
            expectedPegs1.get(1).addFirst(i);
        }

        List<Deque<Integer>> expectedPegs2 = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            expectedPegs2.add(new LinkedList<Integer>());
        }
        for (int i = numRings; i >= 1; --i) {
            expectedPegs2.get(2).addFirst(i);
        }
        if (!pegs.equals(expectedPegs1) && !pegs.equals(expectedPegs2)) {
            throw new TestFailure("Pegs doesn't place in the right configuration");
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "Hanoi.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
