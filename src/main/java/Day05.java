import util.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05 {

    private static final Map<String, List<String>> PATHS = new HashMap<>();

    private static int partOne(List<String> orders) {
        return orders.stream()
                .map(order -> order.split(","))
                .filter(Day05::isValidOrder)
                .map(order -> Integer.parseInt(order[order.length / 2]))
                .reduce(Math::addExact)
                .get();
    }

    private static int partTwo(List<String> orders) {
        return orders.parallelStream()
                .map(order -> order.split(","))
                .filter(order -> !isValidOrder(order))
                .map(Day05::sort)
                .map(order -> Integer.parseInt(order[order.length / 2]))
                .reduce(Math::addExact)
                .get();
    }

    private static String[] sort(String[] order) {
        for (String page : order) {
            List<String> orderList = new ArrayList<>(List.of(order));
            List<String> newOrder = sort(orderList, page);
            if (newOrder != null) {
                return newOrder.toArray(new String[0]);
            }
        }
        throw new IllegalStateException();
    }

    private static List<String> sort(List<String> orderOriginal, String startingPage) {
        ArrayList<String> orderCopy = new ArrayList<>(orderOriginal);
        orderCopy.remove(startingPage);
        if (orderCopy.isEmpty()) {
            return orderCopy;
        }
        List<String> path = PATHS.get(startingPage);
        if (path == null) {
            return null;
        }
        for (String nextPage : path) {
            if (orderCopy.contains(nextPage)) {
                List<String> newOrder = sort(orderCopy, nextPage);
                if (newOrder != null) {
                    newOrder.add(nextPage);
                    return newOrder;
                }
            }
        }
        return null;
    }

    private static boolean isValidOrder(String[] order) {
        for (int i = 0; i < order.length - 1; i++) {
            List<String> path = PATHS.get(order[i]);
            if (path == null || !path.contains(order[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<String> input = FileReader.readLines("Day05");
        int splitIndex = input.indexOf("");
        List<String> rules = new ArrayList<>(input.subList(0, splitIndex));
        List<String> orders = new ArrayList<>(input.subList(splitIndex + 1, input.size()));
        for (String rule : rules) {
            String[] split = rule.split("\\|");
            if (!PATHS.containsKey(split[0])) {
                PATHS.put(split[0], new ArrayList<>());
            }
            PATHS.get(split[0]).add(split[1]);
        }
        System.out.println(partOne(orders));
        System.out.println(partTwo(orders));
    }
}
