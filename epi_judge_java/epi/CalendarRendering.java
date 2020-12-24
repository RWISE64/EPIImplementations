package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class CalendarRendering {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Event {
    public int start, finish;

    public Event(int start, int finish) {
      this.start = start;
      this.finish = finish;
    }
  }

  private static class Endpoint {
    public int time;
    public boolean isStart;

    Endpoint(int time, boolean isStart) {
      this.time = time;
      this.isStart = isStart;
    }
  }

  @EpiTest(testDataFile = "calendar_rendering.tsv")

  /*
  Just compile all the intervals into a list and sort them
  Time: O(nlogn), where n is the number of events
  Space: O(n)
   */
  public static int findMaxSimultaneousEvents(List<Event> A) {
    List<Endpoint> points = new ArrayList<>();
    // Compile times into a single list
    for (Event e : A) {
        points.add(new Endpoint(e.start, true));
        points.add(new Endpoint(e.finish, false));
    }
    // Sort the points
    Collections.sort(points, (p1, p2) -> {
        int comp = Integer.compare(p1.time, p2.time);
        // Default to regular comparison if not equal
        if (comp != 0)
            return comp;
        // If same, want starting intervals to occur first
        else
            return (p1.isStart) ? -1 : 1;
    });
    int maxOverlap = 0;
    int curEvents = 0;
    // Count the overlaps
    for (Endpoint p : points) {
        if (p.isStart) {
            curEvents++;
            maxOverlap = Math.max(maxOverlap, curEvents);
        }
        else
            curEvents--;
    }
    return maxOverlap;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CalendarRendering.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
