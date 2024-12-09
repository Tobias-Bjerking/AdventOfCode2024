import util.FileReader;

import java.awt.*;
import java.math.BigInteger;
import java.util.*;
import java.util.List;

public class Day09 {
    private static BigInteger partOne(List<Integer> input) {
        List<Integer> defraggedList = new ArrayList<>();
        List<Point> currentList = makeCurrentList(input);
        Queue<Point> queue = makeQueue(input);

        Point lastElement = queue.poll();
        Point nextFile = new Point(lastElement.x, 0);
        for (int file = 0; file < currentList.size(); file++) {
            if (!defraggedList.isEmpty() && file / 2 == nextFile.x) {
                int last = defraggedList.getLast() + lastElement.y;
                defraggedList.remove(defraggedList.size() - 1);
                defraggedList.add(last);
                return checkSum(defraggedList);
            }
            if (currentList.get(file).x != -1) {
                defraggedList.add(currentList.get(file).x);
                defraggedList.add(currentList.get(file).y);
            } else {
                for (int space = 0; space < currentList.get(file).y; space++) {
                    if (lastElement.y == 0) {
                        defraggedList.add(nextFile.x);
                        defraggedList.add(nextFile.y);
                        lastElement = queue.poll();
                        nextFile = new Point(lastElement.x, 0);
                    }
                    nextFile.y = nextFile.y + 1;
                    lastElement.y = lastElement.y - 1;
                }
                if (input.get(file + 1) > 0) {
                    defraggedList.add(nextFile.x);
                    defraggedList.add(nextFile.y);
                    nextFile.y = 0;
                }
            }

        }
        return BigInteger.ZERO;
    }

    private static BigInteger partTwo(List<Integer> input) {
        List<Point> currentList = makeCurrentList(input);
        Queue<Point> queue = makeQueue(input);

        Point lastElement;

        while (!queue.isEmpty()) {
            lastElement = queue.poll();
            int index = currentList.indexOf(lastElement);
            if (defraggmentElement(currentList, lastElement, index)) {
                removeMovedElement(currentList, lastElement);
            }
        }
        List<Integer> defraggedList = new ArrayList<>();
        for (Point point : currentList) {
            defraggedList.add(point.x);
            defraggedList.add(point.y);
        }

        return checkSum(defraggedList);
    }

    private static void removeMovedElement(List<Point> currentList, Point lastElement) {
        for (int i = currentList.size() - 1; i >= 0; i--) {
            if (currentList.get(i).x == lastElement.x) {
                currentList.remove(i);
                currentList.add(i, new Point(-1, lastElement.y));
                return;
            }
        }
    }

    private static boolean defraggmentElement(List<Point> currentList, Point lastElement, int index) {
        for (int i = 0; i < currentList.size(); i++) {
            if (index <= i) {
                return false;
            }
            if (currentList.get(i).x == -1) {
                if (currentList.get(i).y >= lastElement.y) {
                    int diff = currentList.get(i).y - lastElement.y;
                    currentList.remove(i);
                    currentList.add(i, lastElement);
                    if (diff > 0) {
                        currentList.add(i + 1, new Point(-1, diff));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private static BigInteger checkSum(List<Integer> defraggedList) {
        BigInteger checkSum = BigInteger.ZERO;
        int index = 0;
        for (int i = 0; i < defraggedList.size(); i += 2) {
            for (int j = 0; j < defraggedList.get(i + 1); j++) {
                if (defraggedList.get(i) != -1) {
                    checkSum = checkSum.add(BigInteger.valueOf(defraggedList.get(i) * index));
                }
                index++;
            }
        }
        return checkSum;
    }

    private static List<Point> makeCurrentList(List<Integer> input) {
        List<Point> currentList = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 2) {
            currentList.add(new Point(i / 2, input.get(i)));
            if (i != input.size() - 1) {
                currentList.add(new Point(-1, input.get(i + 1)));
            }
        }
        return currentList;
    }

    private static Queue<Point> makeQueue(List<Integer> input) {
        Queue<Point> queue = new LinkedList<>();
        for (int i = input.size(); i > 0; i -= 2) {
            queue.add(new Point(i / 2, input.get(i - 1)));
        }
        return queue;
    }

    public static void main(String[] args) {
        List<Integer> input = Arrays.stream(FileReader.readLines("Day09").get(0).split(""))
                .map(Integer::parseInt)
                .toList();

        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
