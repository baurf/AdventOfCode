package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.List;

public class Day07 implements Day {
    @Override
    public String solvePart1(String filePath) {
        List<String> input = Util.getResourceAsStringList(filePath);
        List<List<Long>> equations = parseInput(input);

        long totalCalibrationResult = 0;

        for (List<Long> equation : equations) {
            long targetValue = equation.get(0);
            List<Long> numbers = equation.subList(1, equation.size());

            if (canProduceTarget(numbers, targetValue)) {
                totalCalibrationResult += targetValue;
            }
        }

        return String.valueOf(totalCalibrationResult);
    }

    @Override
    public String solvePart2(String filePath) {
        List<String> input = Util.getResourceAsStringList(filePath);
        List<List<Long>> equations = parseInput(input);

        long totalCalibrationResult = 0;

        for (List<Long> equation : equations) {
            long targetValue = equation.get(0);
            List<Long> numbers = equation.subList(1, equation.size());

            if (canProduceTargetWithConcatenation(numbers, targetValue)) {
                totalCalibrationResult += targetValue;
            }
        }

        return String.valueOf(totalCalibrationResult);
    }

    private List<List<Long>> parseInput(List<String> input) {
        return Util.splitAndMapToLong(input, ": | ");
    }

    private boolean canProduceTargetWithConcatenation(List<Long> numbers, long targetValue) {
        return checkCombinations(numbers, 0, numbers.get(0), targetValue);
    }

    private boolean checkCombinations(List<Long> numbers, int index, long currentValue, long targetValue) {
        if (index == numbers.size() - 1) {
            return currentValue == targetValue;
        }

        long nextNumber = numbers.get(index + 1);

        // Try addition
        if (checkCombinations(numbers, index + 1, currentValue + nextNumber, targetValue)) {
            return true;
        }

        // Try multiplication
        if (checkCombinations(numbers, index + 1, currentValue * nextNumber, targetValue)) {
            return true;
        }

        // Try concatenation
        long concatenatedValue = concatenate(currentValue, nextNumber);
        return checkCombinations(numbers, index + 1, concatenatedValue, targetValue);
    }

    private long concatenate(long a, long b) {
        String concatenated = String.valueOf(a) + b;
        return Long.parseLong(concatenated);
    }

    private boolean canProduceTarget(List<Long> numbers, long targetValue) {
        return checkCombinations(numbers, 0, numbers.get(0), targetValue);
    }
}
