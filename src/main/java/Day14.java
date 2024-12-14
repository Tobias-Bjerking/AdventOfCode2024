import util.FileReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {
    private static final Point upperBound = new Point(101,  103);

    private static int partOne(List<Point[]> input) {
        List<Point> guardLocations = new ArrayList<>();
        for (Point[] guard : input) {
            guardLocations.add(step(guard, 100));
        }
        return calculateSafetyFactor(guardLocations);
    }

    private static Point step(Point[] guard, int steps) {
        int x = (guard[0].x + steps * guard[1].x) % upperBound.x;
        int y = (guard[0].y + steps * guard[1].y) % upperBound.y;
        return new Point(x < 0 ? x + upperBound.x : x, y < 0 ? y + upperBound.y : y);
    }

    private static int calculateSafetyFactor(List<Point> guardLocations) {
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        for (Point guard : guardLocations) {
            if (guard.x < upperBound.x / 2) {
                if (guard.y < upperBound.y / 2) {
                    q1++;
                } else if (guard.y > upperBound.y / 2) {
                    q2++;
                }
            } else if (guard.x > upperBound.x / 2) {
                if (guard.y < upperBound.y / 2) {
                    q3++;
                } else if (guard.y > upperBound.y / 2) {
                    q4++;
                }
            }
        }
        return q1*q2*q3*q4;
    }

    public static void main(String[] args) {
        List<Point[]> input = FileReader.readLines("Day14").stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(s1 -> s1.split("=")[1])
                        .map(s2 -> new Point(Integer.parseInt(s2.split(",")[0]), Integer.parseInt(s2.split(",")[1])))
                        .toArray(Point[]::new))
                .toList();
        System.out.println(partOne(input));
    }
}
