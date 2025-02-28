package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskPairing {
    @EpiUserType(ctorParams = {Integer.class, Integer.class})

    public static class PairedTasks {
        public Integer task1;
        public Integer task2;

        public PairedTasks(Integer task1, Integer task2) {
            this.task1 = task1;
            this.task2 = task2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            PairedTasks that = (PairedTasks) o;

            return task1.equals(that.task1) && task2.equals(that.task2);
        }

        @Override
        public String toString() {
            return "[" + task1 + ", " + task2 + "]";
        }
    }

    @EpiTest(testDataFile = "task_pairing.tsv")

    /*
    Greedy algorithm just sorts the list then pairs the smallest task with the largest then repeats with remaining
    Time: O(nlogn), just the time to sort, iteration is dominated
    Space: O(n), just the space to store the pairs
     */
    public static List<PairedTasks>
    optimumTaskAssignment(List<Integer> taskDurations) {
        // Ensure we have the tasks in increasing order, so we can pair the largest with the smallest
        Collections.sort(taskDurations);
        int i = 0, j = taskDurations.size() - 1;
        List<PairedTasks> pairs = new ArrayList();
        while (i < j) {
            pairs.add(new PairedTasks(taskDurations.get(i), taskDurations.get(j)));
            i++;
            j--;
        }
        return pairs;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "TaskPairing.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
