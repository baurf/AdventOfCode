package aoc.year2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import aoc.common.Day;
import aoc.common.Util;

public class Day01 implements Day {
  @Override
  public String solvePart1(String filePath) {
    List<List<Integer>> lists = mapListContentToInteger(filePath);

    List<Integer> first = lists.getFirst();
    List<Integer> second = lists.getLast();

    Collections.sort(first);
    Collections.sort(second);

    int diff = 0;
    for (int i = 0; i < first.size(); i++) {
      diff += Math.abs(first.get(i) - second.get(i));
    }

    return String.valueOf(diff);
  }

  private static List<List<Integer>> mapListContentToInteger(String filePath) {
    return Util.getResourceAsStringList(filePath, 2, "\\s+")
        .stream()
        .map(it -> it.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new)))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public String solvePart2(String filePath) {
    List<List<Integer>> lists = mapListContentToInteger(filePath);

    List<Integer> first = lists.getFirst();
    List<Integer> second = lists.getLast();

    AtomicInteger similarityScore = new AtomicInteger();
    first.forEach(it -> similarityScore.updateAndGet(v -> v + it * Collections.frequency(second, it)));

    return String.valueOf(similarityScore.get());
  }
}
