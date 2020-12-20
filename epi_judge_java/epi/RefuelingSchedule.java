package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.List;

public class RefuelingSchedule {
    private static final int MPG = 20;

    // gallons[i] is the amount of gas in city i, and distances[i] is the distance
    // city i to the next city.
    /*
    Runtime: O(n)
    Space: O(1) (unless you include the space of gallons and distances
     */
    public static int findAmpleCity(List<Integer> gallons,
                                    List<Integer> distances) {
        // Just need to calculate which city has the minimum amount of gas on arrival
        // By using that city as our starting point, we can ensure we don't dip below 0
        // e.g. we'll have just enough gas to get back
        int min = 0;
        int minInd = 0;
        int total = 0;
        for (int i = 0; i < gallons.size(); i++) {
            // Update min if the current total is a new minimum
            if (total < min) {
                min = total;
                minInd = i;
            }
            total += gallons.get(i) * MPG;
            total -= distances.get(i);
        }
        return minInd;
    }

    @EpiTest(testDataFile = "refueling_schedule.tsv")
    public static void findAmpleCityWrapper(TimedExecutor executor,
                                            List<Integer> gallons,
                                            List<Integer> distances)
            throws Exception {
        int result = executor.run(() -> findAmpleCity(gallons, distances));
        final int numCities = gallons.size();
        int tank = 0;
        for (int i = 0; i < numCities; ++i) {
            int city = (result + i) % numCities;
            tank += gallons.get(city) * MPG - distances.get(city);
            if (tank < 0) {
                throw new TestFailure(String.format("Out of gas on city %d", city));
            }
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "RefuelingSchedule.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
