import util.FileReader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {

    private static long partOne(List<LinkedList<Integer>> input) {
        return input.stream().filter(li -> isValidReport(li, false)).count();
    }

    private static long partTwo(List<LinkedList<Integer>> input) {
        return input.stream().filter(li -> isValidReport(li, true)).count();
    }

    private static boolean isValidReport(LinkedList<Integer> report, boolean checkWithTolerance) {
        boolean increasing = report.get(0) - report.get(1) < 0;
        for (int i = 1; i < report.size(); i++) {
            if (!isValidLevel(report.get(i - 1), report.get(i), increasing)) {
                if (checkWithTolerance) {
                    for (int j = 0; j < report.size(); j++) {
                        LinkedList<Integer> copy = new LinkedList<>(report);
                        copy.remove(j);
                        if (isValidReport(copy, false)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    private static boolean isValidLevel(int level1, int level2, boolean increasing) {
        int diff = level1 - level2;
        return !(Math.abs(diff) > 3 || diff == 0) && !(increasing && diff > 0) && !(!increasing && diff < 0);
    }

    public static void main(String[] args) {
        List<LinkedList<Integer>> input = FileReader.readLines("Day02").stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Integer::valueOf)
                        .collect(Collectors.toCollection(LinkedList::new)))
                .toList();
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
