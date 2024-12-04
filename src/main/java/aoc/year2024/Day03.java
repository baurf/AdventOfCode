package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 implements Day {
    @Override
    public String solvePart1(String filePath) {
        // Read the file content using the Util class
        String fileContent = Util.getResourceAsString(filePath);

        // Calculate the sum of all mul(x, y) results
        int amount = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)")
                .matcher(fileContent)
                .results()
                .mapToInt(it -> Integer.parseInt(it.group(1)) * Integer.parseInt(it.group(2)))
                .sum();

        return String.valueOf(amount);
    }

    @Override
    public String solvePart2(String filePath) {
        String fileContent = Util.getResourceAsString(filePath);

        Matcher matcher = Pattern.compile("(mul\\(([0-9]+),([0-9]+)\\))|(do\\(\\))|(don't\\(\\))")
                .matcher(fileContent);
        List<MatchResult> matchResults = matcher.results().toList();

        boolean isOn = true;
        int amount2 = 0;

        for (MatchResult matchResult : matchResults) {
            if (matchResult.group(1) != null && isOn) {
                amount2 += Integer.parseInt(matchResult.group(2)) * Integer.parseInt(matchResult.group(3));
            } else if (matchResult.group(4) != null) {
                isOn = true;
            } else if (matchResult.group(5) != null) {
                isOn = false;
            }
        }

        return String.valueOf(amount2);
    }
}
