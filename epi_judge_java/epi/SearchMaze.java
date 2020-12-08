package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SearchMaze {
    @EpiUserType(ctorParams = {int.class, int.class})

    public static class Coordinate {
        public int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Coordinate that = (Coordinate) o;
            if (x != that.x || y != that.y) {
                return false;
            }
            return true;
        }
    }

    public enum Color {WHITE, BLACK}

    /*
    Uses DFS to find the path from s to e
    Uses recursion so that the call stack can act as our DFS stack
    Time: O(V + E) where v = number of vertices, e = number of edges
    Space: O(E)? Worst-Case = all nodes somehow contribute to the path, requiring space on the stack
     */
    public static List<Coordinate> searchMaze(List<List<Color>> maze,
                                              Coordinate s, Coordinate e) {
        List<Coordinate> res = new ArrayList();
        // Edge checks, return empty path if the current path is invalid
        if (s.x < 0 || s.y < 0 || s.x >= maze.size() || s.y >= maze.get(s.x).size() || maze.get(s.x).get(s.y) == Color.BLACK)
            return res;
        // Color the node to avoid revisiting
        maze.get(s.x).set(s.y, Color.BLACK);
        res.add(s);
        // If s == e, then we've found the end node and start combining the lists
        if (s.equals(e))
            return res;
        List<Coordinate> path = new ArrayList();
        // Otherwise, we try the path above, below, left, and right of the current node
        for (Coordinate next : List.of(new Coordinate(s.x + 1, s.y), new Coordinate(s.x - 1, s.y),
                new Coordinate(s.x, s.y + 1), new Coordinate(s.x, s.y - 1))) {
            if (!(path = searchMaze(maze, next, e)).isEmpty()) {
                res.addAll(path);
                return res;
            }
        }
        return new ArrayList();
    }

    public static boolean pathElementIsFeasible(List<List<Integer>> maze,
                                                Coordinate prev, Coordinate cur) {
        if (!(0 <= cur.x && cur.x < maze.size() && 0 <= cur.y &&
                cur.y < maze.get(cur.x).size() && maze.get(cur.x).get(cur.y) == 0)) {
            return false;
        }
        return cur.x == prev.x + 1 && cur.y == prev.y ||
                cur.x == prev.x - 1 && cur.y == prev.y ||
                cur.x == prev.x && cur.y == prev.y + 1 ||
                cur.x == prev.x && cur.y == prev.y - 1;
    }

    @EpiTest(testDataFile = "search_maze.tsv")
    public static boolean searchMazeWrapper(List<List<Integer>> maze,
                                            Coordinate s, Coordinate e)
            throws TestFailure {
        List<List<Color>> colored = new ArrayList<>();
        for (List<Integer> col : maze) {
            List<Color> tmp = new ArrayList<>();
            for (Integer i : col) {
                tmp.add(i == 0 ? Color.WHITE : Color.BLACK);
            }
            colored.add(tmp);
        }
        List<Coordinate> path = searchMaze(colored, s, e);
        if (path.isEmpty()) {
            return s.equals(e);
        }

        if (!path.get(0).equals(s) || !path.get(path.size() - 1).equals(e)) {
            throw new TestFailure("Path doesn't lay between start and end points");
        }

        for (int i = 1; i < path.size(); i++) {
            if (!pathElementIsFeasible(maze, path.get(i - 1), path.get(i))) {
                throw new TestFailure("Path contains invalid segments");
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SearchMaze.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
