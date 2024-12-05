package aoc.year2024;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import aoc.common.Day;
import aoc.common.Util;

public class Day05 implements Day {
  @Override
  public String solvePart1(String filePath) {
    HashMap<Integer, List<Integer>> hashmap = new HashMap<>();
    List<List<Integer>> part2 = new ArrayList<>();

    splitFileIntoParts(filePath, part2, hashmap);

    return String.valueOf(part2.stream()
        .filter(it -> isCorrectOrder(it, hashmap))
        .mapToInt(it -> getMidOfArray(it))
        .sum());
  }

  @Override
  public String solvePart2(String filePath) {
    HashMap<Integer, List<Integer>> hashmap = new HashMap<>();
    List<List<Integer>> part2 = new ArrayList<>();

    splitFileIntoParts(filePath, part2, hashmap);

    return String.valueOf(part2.stream()
        .filter(it -> !isCorrectOrder(it, hashmap))
        .map(it -> it.stream().sorted(sortByRules(hashmap)).toList())
        .mapToInt(it -> getMidOfArray(it))
        .sum());
  }

  private static int getMidOfArray(List<Integer> list) {
    int midOfArray = list.size() / 2;
    return list.get(midOfArray);
  }

  private static void splitFileIntoParts(String filePath, List<List<Integer>> part2, HashMap<Integer, List<Integer>> hashmap) {
    List<String> one = Util.getResourceAsStringList(filePath);
    int blankIndex = one.indexOf("");
    List<String> part1Strings = one.subList(0, blankIndex);
    List<String> part2Strings = one.subList(blankIndex + 1, one.size());

    part2.addAll(Util.splitAndMapToInt(part2Strings, ","));

    Util.splitAndMapToInt(part1Strings, "\\|")
        .forEach(
            it -> hashmap.computeIfAbsent(it.getFirst(), k -> new ArrayList<>()).add(it.getLast())
        );
  }

  private boolean isCorrectOrder(List<Integer> update, HashMap<Integer, List<Integer>> rules) {
    for (int i = 0; i < update.size(); i++) {
      int currentPage = update.get(i);
      List<Integer> mustBeAfter = rules.get(currentPage);
      if (mustBeAfter != null) {
        for (int j = 0; j < i; j++) {
          if (mustBeAfter.contains(update.get(j))) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private static Comparator<Integer> sortByRules(HashMap<Integer, List<Integer>> rules) {
    return (a, b) -> {
      if (rules.containsKey(a) && rules.get(a).contains(b)) {
        return -1;
      } else if (rules.containsKey(b) && rules.get(b).contains(a)) {
        return 1;
      } else {
        return 0;
      }
    };
  }
}
