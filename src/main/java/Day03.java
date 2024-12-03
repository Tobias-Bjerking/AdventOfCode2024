import util.FileReader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    private static final Pattern PATTERN_PART_ONE = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
    private static final Pattern PATTERN_PART_TWO = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)");
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    private static int partOne(List<String> input) {
        return runUncorrupted(input, PATTERN_PART_ONE);
    }

    private static int partTwo(List<String> input) {
        return runUncorrupted(input, PATTERN_PART_TWO);
    }

    private static int runUncorrupted(List<String> input, Pattern pattern) {
        int total = 0;
        boolean run = true;
        for (String line : input) {
            Matcher matcherOperations = pattern.matcher(line);
            while (matcherOperations.find()) {
                String operation = matcherOperations.group();
                if (operation.startsWith("mul") && run) {
                    Matcher matcherNumbers = PATTERN_NUMBERS.matcher(operation);
                    matcherNumbers.find();
                    total += Integer.parseInt(matcherNumbers.group(1)) * Integer.parseInt(matcherNumbers.group(2));
                } else {
                    run = "do()".equals(operation);
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        List<String> input = FileReader.readLines("Day03");
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
