import util.FileReader;
import util.Matrix;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day10 {

    private static int partOne(Matrix<Integer> matrix) {
        List<Point> trailheads = matrix.findMatch(0);
        int total = 0;
        for (Point trailhead : trailheads) {
            total += dfs(matrix, trailhead).size();
        }
        return total;
    }

    private static int partTwo(Matrix<Integer> matrix) {
        List<Point> trailheads = matrix.findMatch(0);
        int total = 0;
        for (Point trailhead : trailheads) {
            total += dfsDistinct(matrix, trailhead, 0).size();
        }
        return total;
    }

    private static Set<Point> dfs(Matrix<Integer> matrix, Point position) {
        return new HashSet<>(dfsDistinct(matrix, position, 0));
    }

    private static List<Point> dfsDistinct(Matrix<Integer> matrix, Point position, int height) {
        if (height == 9) {
            return List.of(position);
        }
        List<Point> possiblePathDirections = matrix.checkNeighboursNonDiagonal(position, List.of(height + 1));
        List<Point> trailEnds = new ArrayList<>();
        for (Point possiblePathDirection : possiblePathDirections) {
            trailEnds.addAll(dfsDistinct(matrix, Matrix.addPoints(possiblePathDirection, position), height + 1));
        }
        return trailEnds;
    }

    public static void main(String[] args) {
        Matrix<Integer> matrix = new Matrix<>(FileReader.readLines("Day10").stream()
                .map(string -> Arrays.stream(string.split("")).map(Integer::parseInt).toArray(Integer[]::new))
                .toArray(Integer[][]::new));
        System.out.println(partOne(matrix));
        System.out.println(partTwo(matrix));
    }
}
