package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.Arrays;
import java.util.List;

public class Day02 implements Day {
    @Override
    public String solvePart1(String filePath) {
        List<List<Integer>> levels = Util.getResourceAsStringList(filePath)
                .stream()
                .map(line -> line.split("\\s+"))
                .map(strings -> Arrays.stream(strings).map(Integer::parseInt).toList())
                .toList();

        return String.valueOf(levels.stream().filter(Day02::isReportSafe).count());
    }

    @Override
    public String solvePart2(String filePath) {
        return "";
    }

    private static boolean isReportSafe(List<Integer> integers) {
        boolean isSafe = true;
        boolean isIncreasing = isIncreasing(integers.get(0) - integers.get(1));

        for (int i = 1; i < integers.size(); i++) {
            int diff = integers.get(i - 1) - integers.get(i);
            boolean increasingCurrently = isIncreasing(diff);

            int abs = Math.abs(diff);

            if (isIncreasing != increasingCurrently || abs > 3 || abs < 1) {
                return false;
            }
        }
        return isSafe;
    }

    private static boolean isIncreasing(Integer diff) {
        return diff > 0;
    }

}
