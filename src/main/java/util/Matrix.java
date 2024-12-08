package util;

import java.awt.Point;
import java.util.*;

public class Matrix<T> {

    private T[][] matrix;

    public static final Point NORTH = new Point(0, -1);
    public static final Point NORTH_EAST = new Point(1, -1);
    public static final Point EAST = new Point(1, 0);
    public static final Point SOUTH_EAST = new Point(1, 1);
    public static final Point SOUTH = new Point(0, 1);
    public static final Point SOUTH_WEST = new Point(-1, 1);
    public static final Point WEST = new Point(-1, 0);
    public static final Point NORTH_WEST = new Point(-1, -1);
    public static final List<Point> DIRECTIONS = List.of(NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST);
    public static final List<Point> CORNERS = List.of(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public T get(Point position) {
        return matrix[position.y][position.x];
    }

    public List<Point> findMatches(List<T> match) {
        List<Point> startingPoints = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                Point point = new Point(x, y);
                if (checkPosition(point, match.get(0))) {
                    startingPoints.add(point);
                }
            }
        }
        return startingPoints.stream()
                .flatMap(sp -> checkNeighbours(sp, match.subList(1, match.size())).stream())
                .toList();
    }

    public List<Point> findMatch(T match) {
        return findMatches(List.of(match));
    }

    public Set<T> findTypes() {
        Set<T> types = new HashSet<>();
        for (int y = 0; y < matrix.length; y++) {
            types.addAll(Arrays.asList(matrix[y]));
        }
        return types;
    }

    public List<Point> checkNeighbours(Point position, List<T> match) {
        if (match.isEmpty())
            return List.of(position);
        List<Point> foundMatchDirections = new ArrayList<>();
        for (Point direction : DIRECTIONS) {
            if (checkDirection(position, direction, match)) {
                foundMatchDirections.add(direction);
            }
        }
        return foundMatchDirections;
    }

    public boolean checkDirection(Point position, Point direction, List<T> match) {
        Stack<T> stack = new Stack<>();
        stack.addAll(match.reversed());
        Point currentPosition = position;
        while (!stack.isEmpty()) {
            T pop = stack.pop();
            currentPosition = addPoints(currentPosition, direction);
            if (!checkPosition(currentPosition, pop)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDirection(Point position, Point direction, T match) {
        return checkDirection(position, direction, List.of(match));
    }

    public boolean checkPosition(Point position, T match) {
        T value = null;
        try {
            value = get(position);
        } catch (IndexOutOfBoundsException ignored) {
        }
        return match.equals(value);
    }

    public Point getSize() {
        return new Point(matrix[0].length, matrix.length);
    }
    public void set(Point point, T t) {
        matrix[point.y][point.x] = t;
    }

    public void print() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                System.out.print(matrix[y][x]);
            }
            System.out.println("");
        }

    }

    public static Point addPoints(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    public static Point subtractPoints(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }

}
