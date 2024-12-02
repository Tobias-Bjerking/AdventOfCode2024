import util.FileReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day01 {

    private static Integer partOne(List<Point> input) {
        return input.stream()
                .map(p -> Math.abs(p.x - p.y))
                .reduce(Math::addExact)
                .get();
    }

    private static Integer partTwo(List<Point> input) {
        List<Integer> output = new ArrayList<>();
        for (Point p1 : input) {
            int total = 0;
            for (Point p2 : input) {
                if (p1.x == p2.y) {
                    total++;
                }
            }
            output.add(total * p1.x);
        }
        return output.stream().reduce(Math::addExact).get();
    }

    private static List<Point> sort(List<String> list) {
        Integer[] columnOne = new Integer[list.size()];
        Integer[] columnTwo = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String[] split = list.get(i).split("   ");
            columnOne[i] = (Integer.valueOf(split[0]));
            columnTwo[i] = (Integer.valueOf(split[1]));
        }
        Arrays.sort(columnOne);
        Arrays.sort(columnTwo);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            points.add(new Point(columnOne[i], columnTwo[i]));
        }
        return points;
    }

    public static void main(String[] args) {
        List<String> input = FileReader.readLines("Day01");
        List<Point> sortedInput = sort(input);
        System.out.println(partOne(sortedInput));
        System.out.println(partTwo(sortedInput));
    }
}
