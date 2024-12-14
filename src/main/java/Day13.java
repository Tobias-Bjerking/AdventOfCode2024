import util.FileReader;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    private static BigInteger partOne(List<List<BigInteger>> inputs) {
        BigInteger tokens = BigInteger.ZERO;
        for (List<BigInteger> input : inputs) {
            Point xy = calculateCramersRule(input);
            if (xy != null) {
                tokens = tokens.add(xy.x.multiply(BigInteger.valueOf(3))).add(xy.y);
            }
        }
        return tokens;
    }

    private static BigInteger partTwo(List<List<BigInteger>> inputs) {
        BigInteger unitOfset = new BigInteger("10000000000000");
        for (List<BigInteger> input : inputs) {
            input.set(4, input.get(4).add(unitOfset));
            input.set(5, input.get(5).add(unitOfset));
        }
        return partOne(inputs);
    }

    private static Point calculateCramersRule(List<BigInteger> input) {
        Point a = new Point(input.get(0), input.get(1));
        Point b = new Point(input.get(2), input.get(3));
        Point c = new Point(input.get(4), input.get(5));
        BigInteger d = determinant(a, b);
        BigInteger dx = determinant(c, b);
        BigInteger dy = determinant(a, c);
        BigDecimal x;
        BigDecimal y;
        try {
            x = new BigDecimal(dx).divide(new BigDecimal(d));
            y = new BigDecimal(dy).divide(new BigDecimal(d));
        } catch (ArithmeticException e) {
            return null;
        }
        if (!(x.stripTrailingZeros().scale() <= 0) || !(y.stripTrailingZeros().scale() <= 0)) {
            return null;
        }
        return new Point(x.toBigInteger(), y.toBigInteger());
    }

    private static BigInteger determinant(Point vector1, Point vector2) {
        return vector1.x.multiply(vector2.y).subtract(vector2.x.multiply(vector1.y));
    }

    public static void main(String[] args) {
        List<String> list = FileReader.readLines("Day13");
        Pattern pattern = Pattern.compile("[-+]?\\d+");
        List<List<BigInteger>> input = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 4) {
            List<BigInteger> integers = new ArrayList<>();
            for (int j = i; j < i + 3; j++) {
                Matcher matcher = pattern.matcher(list.get(j));
                while (matcher.find()) {
                    integers.add(new BigInteger(matcher.group()));
                }
            }
            input.add(integers);
        }
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }

    private record Point(BigInteger x, BigInteger y) {
    }

}
