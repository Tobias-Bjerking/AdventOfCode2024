import util.FileReader;
import util.Matrix;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Day06 {

    private static Map<Point, List<Point>> partOne(Matrix<Character> matrix) {
        HashMap<Point, List<Point>> visited = new HashMap<>();
        Point position = matrix.findMatch('^').getFirst();
        Point direction = Matrix.NORTH;
        while (matrix.hasPoint(position)) {
            if (matrix.checkDirection(position, direction, '#')) {
                direction = turnRight(direction);
            } else {
                if (visited.containsKey(position)) {
                    if (visited.get(position).contains(direction)) {
                        return null;
                    }
                } else {
                    visited.put(position, new ArrayList<>());
                }
                visited.get(position).add(direction);
                position = Matrix.addPoints(position, direction);
            }
        }
        return visited;
    }

    private static int partTwo(Matrix<Character> matrix) {
        HashSet<Point> found = new HashSet<>();
        Point originalPosition = matrix.findMatch('^').getFirst();
        Map<Point, List<Point>> path = partOne(matrix);
        for (Point point : path.keySet()) {
            if (!point.equals(originalPosition)) {
                Character originalCharacter = matrix.get(point);
                matrix.set(point, '#');
                if (partOne(matrix) == null) {
                    found.add(point);
                }
                matrix.set(point, originalCharacter);
            }
        }
        return found.size();
    }

    private static Point turnRight(Point direction) {
        return new Point(-direction.y, direction.x);
    }

    public static void main(String[] args) {
        Matrix<Character> matrix = new Matrix<>(FileReader.readLines("Day06").stream()
                .map(string -> string.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new));
        System.out.println(partOne(matrix).size());
        System.out.println(partTwo(matrix));
    }
}
