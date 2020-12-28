package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Just implemented a BFS
Should have a runtime of O(mn) and less than O(mn) space complexity
 */
public class MatrixConnectedRegions {
    static class Coordinate {
        int x;
        int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void flipColor(int x, int y, List<List<Boolean>> image) {
        ArrayDeque<Coordinate> q = new ArrayDeque<>();
        boolean color = image.get(x).get(y);
        q.add(new Coordinate(x, y));
        while (!q.isEmpty()) {
            Coordinate cur = q.poll();
            if (cur.x < 0 || cur.x >= image.size() || cur.y < 0 || cur.y >= image.get(0).size())
                continue;
            if (image.get(cur.x).get(cur.y) == color) {
                image.get(cur.x).set(cur.y, !color);
                q.addAll(Arrays.asList(new Coordinate(cur.x + 1, cur.y), new Coordinate(cur.x - 1, cur.y),
                        new Coordinate(cur.x, cur.y + 1), new Coordinate(cur.x, cur.y - 1)));
            }
        }
        return;
    }

    @EpiTest(testDataFile = "painting.tsv")
    public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                       int x, int y,
                                                       List<List<Integer>> image)
            throws Exception {
        List<List<Boolean>> B = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            B.add(new ArrayList<>());
            for (int j = 0; j < image.get(i).size(); j++) {
                B.get(i).add(image.get(i).get(j) == 1);
            }
        }

        executor.run(() -> flipColor(x, y, B));

        image = new ArrayList<>();
        for (int i = 0; i < B.size(); i++) {
            image.add(new ArrayList<>());
            for (int j = 0; j < B.get(i).size(); j++) {
                image.get(i).add(B.get(i).get(j) ? 1 : 0);
            }
        }

        return image;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
