import util.FileReader;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    private static BigInteger partOne(List<BigInteger> stones) {
        return countStones(stones, 25);
    }

    private static BigInteger partTwo(List<BigInteger> stones) {
        return countStones(stones, 75);
    }

    private static BigInteger countStones(List<BigInteger> stones, int numberOfBlinks) {
        Map<BigInteger, BigInteger> bucket = stones.stream().collect(Collectors.toMap(key -> key, value -> BigInteger.ONE));
        for (int i = 0; i < numberOfBlinks; i++) {
            blink(bucket);
        }
        return bucket.values().stream().reduce(BigInteger::add).get();
    }

    private static void blink(Map<BigInteger, BigInteger> bucket) {
        for (Map.Entry<BigInteger, BigInteger> entry : Map.copyOf(bucket).entrySet()) {
            if (!entry.getValue().equals(BigInteger.ZERO)) {
                if (entry.getKey().equals(BigInteger.ZERO)) {
                    incrementBucket(bucket, BigInteger.ONE, entry.getValue());
                } else if (entry.getKey().toString().length() % 2 == 0) {
                    incrementBucket(bucket, new BigInteger(entry.getKey().toString().substring(0, entry.getKey().toString().length() / 2)), entry.getValue());
                    incrementBucket(bucket, new BigInteger(entry.getKey().toString().substring(entry.getKey().toString().length() / 2)), entry.getValue());
                } else {
                    incrementBucket(bucket, entry.getKey().multiply(BigInteger.valueOf(2024)), entry.getValue());
                }
                decrementBucket(bucket, entry.getKey(), entry.getValue());
            }
        }
    }

    private static void incrementBucket(Map<BigInteger, BigInteger> bucket, BigInteger stone, BigInteger numberOfNewStones) {
        if (!bucket.containsKey(stone)) {
            bucket.put(stone, BigInteger.ZERO);
        }
        BigInteger totalNumberOfStones = bucket.get(stone).add(numberOfNewStones);
        bucket.put(stone, totalNumberOfStones);
    }

    private static void decrementBucket(Map<BigInteger, BigInteger> bucket, BigInteger stone, BigInteger numberOfNewStones) {
        BigInteger totalNumberOfStones = bucket.get(stone).subtract(numberOfNewStones);
        bucket.put(stone, totalNumberOfStones);
    }

    public static void main(String[] args) {
        List<BigInteger> input = Arrays.stream(FileReader.readLines("Day11").get(0).split(" ")).map(BigInteger::new).toList();
        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
