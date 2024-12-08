import util.FileReader;
import util.Matrix;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 {
    private static Set<Point> partOne(Matrix<Character> matrix) {
        Set<Point> antinodes = new HashSet<>();
        Set<Character> types = matrix.findTypes();
        types.remove('.');

        for (Character type : types) {
            List<Point> antennas = matrix.findMatch(type);
            for (Point antenna : antennas) {
                for (Point comparing : antennas) {
                    Point diff = Matrix.subtractPoints(antenna, comparing);
                    if (!(diff.x == 0 && diff.y == 0)) {
                        Point antinode = Matrix.addPoints(antenna, diff);
                        if (antinode.x >= 0 && antinode.x < matrix.getSize().x && antinode.y >= 0 && antinode.y < matrix.getSize().y) {
                            antinodes.add(antinode);
                        }
                    }
                }
            }
        }
        return antinodes;
    }

    private static Set<Point> partTwo(Matrix<Character> matrix) {
        Set<Point> antinodes = new HashSet<>();
        Set<Character> types = matrix.findTypes();
        types.remove('.');

        for (Character type : types) {
            List<Point> antennas = matrix.findMatch(type);
            for (Point antenna : antennas) {
                if (antennas.size() >= 3) {
                    antinodes.add(antenna);
                }
                for (Point comparing : antennas) {
                    Point diff = Matrix.subtractPoints(antenna, comparing);
                    if (!(diff.x == 0 && diff.y == 0)) {
                        Point antinode = Matrix.addPoints(antenna, diff);
                        while (antinode.x >= 0 && antinode.x < matrix.getSize().x && antinode.y >= 0 && antinode.y < matrix.getSize().y) {
                            antinodes.add(antinode);
                            antinode = Matrix.addPoints(antinode, diff);
                        }
                    }
                }
            }
        }
        return antinodes;
    }

    public static void main(String[] args) {
        Matrix<Character> matrix = new Matrix<>(FileReader.readLines("Day08").stream()
                .map(string -> string.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new));
        System.out.println(partOne(matrix).size());
        System.out.println(partTwo(matrix).size());
    }
}
