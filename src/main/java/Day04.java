import util.FileReader;
import util.Matrix;

import java.awt.*;
import java.util.List;

public class Day04 {

    private static long partOne(Matrix<Character> matrix) {
        return matrix.findMatches(List.of('X', 'M', 'A', 'S')).stream().count();

    }

    private static int partTwo(Matrix<Character> matrix) {
        int total = 0;
        List<Point> startingPoints = matrix.findMatches(List.of('A'));
        List<Character> mas = List.of('M', 'A', 'S');
        for (Point startingPoint : startingPoints) {
            int cross = 0;
            for (Point cornerDirection : Matrix.CORNERS) {
                Point point = Matrix.addPoints(Matrix.addPoints(startingPoint, cornerDirection), cornerDirection);
                Point direction = new Point(cornerDirection.x * -1, cornerDirection.y * -1);
                if (matrix.checkDirection(point, direction, mas)) {
                    cross++;
                }
            }
            if (cross == 2) {
                total++;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Character[][] input = FileReader.readLines("Day04").stream()
                .map(string -> string.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new);
        Matrix<Character> matrix = new Matrix<>(input);
        System.out.println(partOne(matrix));
        System.out.println(partTwo(matrix));
    }

}
