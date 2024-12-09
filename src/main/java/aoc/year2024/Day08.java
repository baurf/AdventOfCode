package aoc.year2024;

import aoc.common.Coord;
import aoc.common.Day;
import aoc.common.Grid;
import aoc.common.Util;

import java.util.*;

public class Day08 implements Day {

    @Override
    public String solvePart1(String filePath) {
        Grid<Character> grid = Util.generateGrid(filePath);
        Set<Coord> antinodes = findAntinodes(grid);
        return String.valueOf(antinodes.size());
    }

    @Override
    public String solvePart2(String filePath) {
        Grid<Character> grid = Util.generateGrid(filePath);
        Set<Coord> antinodes = findExtendedAntinodes(grid);
        return String.valueOf(antinodes.size());
    }

    private Set<Coord> findAntinodes(Grid<Character> grid) {
        Map<Character, List<Coord>> antennaMap = groupCoordinatesByValue(grid);
        Set<Coord> antinodes = new HashSet<>();

        for (var positions : antennaMap.values()) {
            addAntinodesFromPairs(grid, positions, antinodes);
        }

        return antinodes;
    }

    private Set<Coord> findExtendedAntinodes(Grid<Character> grid) {
        Map<Character, List<Coord>> antennaMap = groupCoordinatesByValue(grid);
        Set<Coord> antinodes = new HashSet<>();

        for (var positions : antennaMap.values()) {
            antinodes.addAll(positions);
            addExtendedAntinodesFromPairs(grid, positions, antinodes);
        }

        return antinodes;
    }

    private Map<Character, List<Coord>> groupCoordinatesByValue(Grid<Character> grid) {
        Map<Character, List<Coord>> antennaMap = new HashMap<>();
        for (var entry : grid.entrySet()) {
            char value = entry.getValue().getValue();
            if (Character.isLetterOrDigit(value)) {
                antennaMap.computeIfAbsent(value, k -> new ArrayList<>()).add(entry.getKey());
            }
        }
        return antennaMap;
    }

    private void addAntinodesFromPairs(Grid<Character> grid, List<Coord> positions, Set<Coord> antinodes) {
        for (int i = 0; i < positions.size(); i++) {
            for (int j = 0; j < positions.size(); j++) {
                if (i == j) continue;

                Coord p1 = positions.get(i);
                Coord p2 = positions.get(j);

                int dx = p2.x() - p1.x();
                int dy = p2.y() - p1.y();

                Coord antinode1 = new Coord(p1.x() - dx, p1.y() - dy);
                Coord antinode2 = new Coord(p2.x() + dx, p2.y() + dy);

                if (grid.contains(antinode1)) {
                    antinodes.add(antinode1);
                }
                if (grid.contains(antinode2)) {
                    antinodes.add(antinode2);
                }
            }
        }
    }

    private void addExtendedAntinodesFromPairs(Grid<Character> grid, List<Coord> positions, Set<Coord> antinodes) {
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Coord p1 = positions.get(i);
                Coord p2 = positions.get(j);

                int dx = p2.x() - p1.x();
                int dy = p2.y() - p1.y();

                int gcd = gcd(Math.abs(dx), Math.abs(dy));
                dx /= gcd;
                dy /= gcd;

                extendLine(grid, antinodes, p1, dx, dy, 1);
                extendLine(grid, antinodes, p1, dx, dy, -1);
            }
        }
    }

    private void extendLine(Grid<Character> grid, Set<Coord> antinodes, Coord start, int dx, int dy, int direction) {
        int x = start.x();
        int y = start.y();

        while (true) {
            x += direction * dx;
            y += direction * dy;
            Coord antinode = new Coord(x, y);

            if (!grid.contains(antinode)) {
                break;
            }

            antinodes.add(antinode);
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
}
