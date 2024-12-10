package aoc.year2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.common.Coord;
import aoc.common.Day;
import aoc.common.Direction;
import aoc.common.Grid;
import aoc.common.Util;

public class Day10 implements Day {
  @Override
  public String solvePart1(String filePath) {
    Grid<Integer> one = Util.generateIntegerGrid(filePath);
    int currentHeight = 0;

    List<Coord> trailheads = one.getCoordOfValue(currentHeight);
    Map<Coord, Set<Coord>> trailheadToReachedNines = new HashMap<>();
    for (Coord trailhead : trailheads) {
      List<Coord> reachedNines = checkPath(trailhead, one, currentHeight, new ArrayList<>());
      trailheadToReachedNines.put(trailhead, new HashSet<>(reachedNines));
    }

    for (Map.Entry<Coord, Set<Coord>> entry : trailheadToReachedNines.entrySet()) {
      System.out.println("Trailhead: " + entry.getKey() + " -> Reached 9s: " + entry.getValue());
    }

    int totalScore = trailheadToReachedNines.values().stream().mapToInt(Set::size).sum();

    return String.valueOf(totalScore);
  }

  private static List<Coord> checkPath(Coord trailhead, Grid<Integer> one, int currentHeight, List<Coord> path) {
    path.add(trailhead);

    if (currentHeight == 9) {
      System.out.println("Full path: " + path);
      List<Coord> result = new ArrayList<>();
      result.add(trailhead);
      return result; // Reached height 9, return this coordinate
    }

    List<Coord> nextStep = new ArrayList<>();
    List<Coord> reachedNines = new ArrayList<>();

    one.setCurrentPos(trailhead);
    for (Direction direction : Direction.values()) {
      Coord next = one.getNextCoord(direction);
      if (next != null && one.contains(next)) {
        int val = one.get(next);
        if (val == currentHeight + 1) {
          nextStep.add(next);
        }
      }
    }

    for (Coord next : nextStep) {
      reachedNines.addAll(checkPath(next, one, currentHeight + 1, new ArrayList<>(path)));
    }

    return reachedNines;
  }

  @Override
  public String solvePart2(String filePath) {
    Grid<Integer> one = Util.generateIntegerGrid(filePath);
    int currentHeight = 0;

    List<Coord> trailheads = one.getCoordOfValue(currentHeight);
    List<Coord> reachedNines = new ArrayList<>();
    for (Coord trailhead : trailheads) {
      reachedNines.addAll(checkPath(trailhead, one, currentHeight, new ArrayList<>()));
    }

    return String.valueOf(reachedNines.size());
  }
}
