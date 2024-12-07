import util.FileReader;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day07 {

    private static String partOne(List<String> input) {
        return input.stream()
                .filter(calibration -> isValidEquation(calibration, List.of("+", "*")))
                .map(s -> s.split(":")[0])
                .map(BigInteger::new)
                .reduce(BigInteger::add)
                .get()
                .toString();
    }

    private static String partTwo(List<String> input) {
        return input.stream()
                .filter(calibration -> isValidEquation(calibration, List.of("+", "*", "||")))
                .map(s -> s.split(":")[0])
                .map(BigInteger::new)
                .reduce(BigInteger::add)
                .get()
                .toString();
    }

    private static boolean isValidEquation(String calibration, List<String> operations) {
        BigInteger testValue = new BigInteger(calibration.split(":")[0]);
        Integer[] equation = Arrays.stream(calibration.split(": ")[1].split(" "))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
        return testEquation(testValue, Arrays.copyOfRange(equation, 1, equation.length), BigInteger.valueOf(equation[0]), operations);
    }

    private static boolean testEquation(BigInteger testValue, Integer[] equation, BigInteger total, List<String> operations) {
        if (total.compareTo(testValue) > 0) {
            return false;
        }
        if (equation.length == 0) {
            return testValue.equals(total);
        }
        for (String operation : operations) {
            if (operation.equals("+")) {
                if (testEquation(testValue, Arrays.copyOfRange(equation, 1, equation.length), total.add(BigInteger.valueOf(equation[0])), operations)) {
                    return true;
                }
            } else if (operation.equals("*")) {
                if (testEquation(testValue, Arrays.copyOfRange(equation, 1, equation.length), total.multiply(BigInteger.valueOf(equation[0])), operations)) {
                    return true;
                }
            } else if (operation.equals("||")) {
                if (testEquation(testValue, Arrays.copyOfRange(equation, 1, equation.length), new BigInteger(total.toString() + equation[0]), operations)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<String> input = FileReader.readLines("Day07");
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }


}
