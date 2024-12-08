package aoc.year2024;

import aoc.common.Day;
import aoc.common.Util;

import java.util.*;

public class Day08 implements Day {

    @Override
    public String solvePart1(String filePath) {
        var grid = Util.generateGrid(filePath);
        Set<String> antinodes = findAntinodes(grid);
        return String.valueOf(antinodes.size());
    }

    @Override
    public String solvePart2(String filePath) {
        var grid = Util.generateGrid(filePath);
        Set<aoc.common.Coord> antinodes = findAntinodesPart2(grid);

        System.out.println("Grid with antinodes (Part 2):");
        printGridWithAntinodes(grid, antinodes);

        return String.valueOf(antinodes.size());
    }

    private Set<String> findAntinodes(aoc.common.Grid<Character> grid) {
        Map<Character, List<aoc.common.Coord>> antennaMap = new HashMap<>();
        Set<String> antinodeSet = new HashSet<>();

        for (var entry : grid.entrySet()) {
            char value = entry.getValue().getValue();
            if (Character.isLetterOrDigit(value)) {
                antennaMap.computeIfAbsent(value, k -> new ArrayList<>()).add(entry.getKey());
            }
        }

        for (var entry : antennaMap.entrySet()) {
            List<aoc.common.Coord> positions = entry.getValue();

            for (int i = 0; i < positions.size(); i++) {
                for (int j = 0; j < positions.size(); j++) {
                    if (i == j) continue;

                    var p1 = positions.get(i);
                    var p2 = positions.get(j);

                    var dx = p2.x() - p1.x();
                    var dy = p2.y() - p1.y();

                    var antinode1 = new aoc.common.Coord(p1.x() - dx, p1.y() - dy);
                    var antinode2 = new aoc.common.Coord(p2.x() + dx, p2.y() + dy);

                    if (grid.contains(antinode1)) {
                        antinodeSet.add(antinode1.toString());
                    }
                    if (grid.contains(antinode2)) {
                        antinodeSet.add(antinode2.toString());
                    }
                }
            }
        }

        return antinodeSet;
    }

    private Set<aoc.common.Coord> findAntinodesPart2(aoc.common.Grid<Character> grid) {
        Map<Character, List<aoc.common.Coord>> antennaMap = new HashMap<>();
        Set<aoc.common.Coord> antinodeSet = new HashSet<>();

        for (var entry : grid.entrySet()) {
            char value = entry.getValue().getValue();
            if (Character.isLetterOrDigit(value)) {
                antennaMap.computeIfAbsent(value, k -> new ArrayList<>()).add(entry.getKey());
            }
        }

        for (var entry : antennaMap.entrySet()) {
            List<aoc.common.Coord> positions = entry.getValue();

            antinodeSet.addAll(positions);

            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    var p1 = positions.get(i);
                    var p2 = positions.get(j);

                    int dx = p2.x() - p1.x();
                    int dy = p2.y() - p1.y();

                    int gcd = gcd(Math.abs(dx), Math.abs(dy));
                    dx /= gcd;
                    dy /= gcd;

                    extendLine(grid, antinodeSet, p1, dx, dy, 1);

                    extendLine(grid, antinodeSet, p1, dx, dy, -1);
                }
            }
        }

        return antinodeSet;
    }

    private void extendLine(aoc.common.Grid<Character> grid, Set<aoc.common.Coord> antinodeSet,
                            aoc.common.Coord start, int dx, int dy, int direction) {
        int x = start.x();
        int y = start.y();

        while (true) {
            x += direction * dx;
            y += direction * dy;
            var antinode = new aoc.common.Coord(x, y);

            if (!grid.contains(antinode)) {
                break;
            }

            antinodeSet.add(antinode);
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private void printGridWithAntinodes(aoc.common.Grid<Character> grid, Set<aoc.common.Coord> antinodes) {
        // Determine grid bounds
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (var entry : grid.entrySet()) {
            var coord = entry.getKey();
            minX = Math.min(minX, coord.x());
            maxX = Math.max(maxX, coord.x());
            minY = Math.min(minY, coord.y());
            maxY = Math.max(maxY, coord.y());
        }

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                var coord = new aoc.common.Coord(x, y);
                if (antinodes.contains(coord)) {
                    System.out.print("#");
                } else if (grid.contains(coord)) {
                    System.out.print(grid.get(coord));
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }


}
