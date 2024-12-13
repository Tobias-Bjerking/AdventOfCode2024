import util.FileReader;
import util.Matrix;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day12 {

    private static int partOne(Matrix<Character> matrix) {
        Set<Character> types = matrix.findTypes();
        int total = 0;
        for (Character type : types) {
            List<Point> locations = matrix.findMatches(List.of(type));
            Set<Point> visitedLocations = new HashSet<>();

            for (Point location : locations) {
                if (!visitedLocations.contains(location)) {
                    List<Point> group = getGroup(matrix, location, visitedLocations);
                    total += group.size() * getPerimeter(matrix, group);
                }
            }
        }
        return total;
    }

    private static int partTwo(Matrix<Character> matrix) {
        Set<Character> types = matrix.findTypes();
        int total = 0;
        for (Character type : types) {
            List<Point> locations = matrix.findMatches(List.of(type));
            Set<Point> visitedLocations = new HashSet<>();

            for (Point location : locations) {
                if (!visitedLocations.contains(location)) {
                    int sides = 0;
                    List<Point> group = getGroup(matrix, location, visitedLocations);
                    for (Point direction : Matrix.DIRECTIONS_NON_DIAGONAL) {
                        Set<Point> pointsWithEdge = getPointsWithEdge(matrix, group, direction);
                        sides += calculateSides(pointsWithEdge, direction);
                    }
                    total += group.size() * sides;
                }
            }
        }
        return total;
    }

    private static Set<Point> getPointsWithEdge(Matrix matrix, List<Point> group, Point direction) {
        Set<Point> pointsWithEdge = new HashSet<>();
        for (Point type : group) {
            if (!matrix.checkPosition(Matrix.addPoints(type, direction), matrix.get(type))) {
                pointsWithEdge.add(type);
            }
        }
        return pointsWithEdge;
    }

    private static int calculateSides(Set<Point> pointsWithEdge, Point direction) {
        int totalNumberOfSides = 0;
        Set<Point> checkedPoint = new HashSet<>();
        for (Point point : pointsWithEdge) {
            if (!checkedPoint.contains(point)) {
                totalNumberOfSides++;
                checkedPoint.add(point);
                Point location = Matrix.addPoints(point, Matrix.turnLeft(direction));
                while (pointsWithEdge.contains(location)) {
                    checkedPoint.add(location);
                    location = Matrix.addPoints(location, Matrix.turnLeft(direction));
                }
                location = Matrix.addPoints(point, Matrix.turnRight(direction));
                while (pointsWithEdge.contains(location)) {
                    checkedPoint.add(location);
                    location = Matrix.addPoints(location, Matrix.turnRight(direction));
                }

            }
        }
        return totalNumberOfSides;
    }

    private static int getPerimeter(Matrix<Character> matrix, List<Point> group) {
        int perimiter = 0;
        for (Point point : group) {
            perimiter += 4 - matrix.checkNeighboursNonDiagonal(point, List.of(matrix.get(point))).size();
        }
        return perimiter;
    }

    private static ArrayList<Point> getGroup(Matrix<Character> matrix, Point position, Set<Point> visitedLocations) {
        ArrayList<Point> group = new ArrayList<>();
        group.add(position);
        visitedLocations.add(position);
        List<Point> matchingNeighbours = matrix.checkNeighboursNonDiagonal(position, List.of(matrix.get(position)));
        for (Point matchingNeighbourDirection : matchingNeighbours) {
            if (!visitedLocations.contains(Matrix.addPoints(position, matchingNeighbourDirection))) {
                group.addAll(getGroup(matrix, Matrix.addPoints(position, matchingNeighbourDirection), visitedLocations));
            }
        }
        return group;
    }

    public static void main(String[] args) {
        Matrix<Character> matrix = new Matrix<>(FileReader.readLines("Day12").stream()
                .map(string -> string.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new));
        System.out.println(partOne(matrix));
        System.out.println(partTwo(matrix));
    }
}
