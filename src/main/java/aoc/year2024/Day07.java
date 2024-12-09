package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.List;

public class Day07 implements Day {

    @Override
    public String solvePart1(String filePath) {
        List<String> input = Util.getResourceAsStringList(filePath);
        List<List<Long>> equations = parseInput(input);

        long totalCalibrationResult = equations.stream()
                .filter(equation -> canProduceTarget(equation, false))
                .mapToLong(equation -> equation.get(0))
                .sum();

        return String.valueOf(totalCalibrationResult);
    }

    @Override
    public String solvePart2(String filePath) {
        List<String> input = Util.getResourceAsStringList(filePath);
        List<List<Long>> equations = parseInput(input);

        long totalCalibrationResult = equations.stream()
                .filter(equation -> canProduceTarget(equation, true))
                .mapToLong(equation -> equation.get(0))
                .sum();

        return String.valueOf(totalCalibrationResult);
    }

    private List<List<Long>> parseInput(List<String> input) {
        return Util.splitAndMapToLong(input, ": | ");
    }

    private boolean canProduceTarget(List<Long> equation, boolean allowConcatenation) {
        long targetValue = equation.get(0);
        List<Long> numbers = equation.subList(1, equation.size());
        return checkCombinations(numbers, 0, numbers.get(0), targetValue, allowConcatenation);
    }

    private boolean checkCombinations(List<Long> numbers, int index, long currentValue, long targetValue, boolean allowConcatenation) {
        if (index == numbers.size() - 1) {
            return currentValue == targetValue;
        }

        long nextNumber = numbers.get(index + 1);

        if (checkCombinations(numbers, index + 1, currentValue + nextNumber, targetValue, allowConcatenation)) {
            return true;
        }

        if (checkCombinations(numbers, index + 1, currentValue * nextNumber, targetValue, allowConcatenation)) {
            return true;
        }

        if (allowConcatenation) {
            long concatenatedValue = concatenate(currentValue, nextNumber);
            return checkCombinations(numbers, index + 1, concatenatedValue, targetValue, allowConcatenation);
        }

        return false;
    }

    private long concatenate(long a, long b) {
        return Long.parseLong(a + String.valueOf(b));
    }
}
