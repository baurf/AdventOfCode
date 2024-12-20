package aoc.year2024;

import aoc.common.Coord;
import aoc.common.Day;
import aoc.common.Util;

import java.util.List;

public class Day04 implements Day {
    private static final List<Coord> ALL_COORDS = List.of(
            new Coord(-1, -1), new Coord(-1, 0), new Coord(-1, 1),
            new Coord(0, -1), new Coord(0, 1),
            new Coord(1, -1), new Coord(1, 0), new Coord(1, 1)
    );

    private static final List<Coord> X_COORDS = List.of(
            new Coord(1, 1), new Coord(-1, -1), new Coord(-1, 1), new Coord(1, -1)
    );

    @Override
    public String solvePart1(String filePath) {
        List<char[]> lines = Util.getResourceAsStringList(filePath)
                .stream()
                .map(String::toCharArray)
                .toList();

        int xMasFound = getxMasFound1(lines);
        return String.valueOf(xMasFound);
    }

    @Override
    public String solvePart2(String filePath) {
        List<char[]> lines = Util.getResourceAsStringList(filePath)
                .stream()
                .map(String::toCharArray)
                .toList();

        int xMasFound2 = getxMasFound2(lines);
        return String.valueOf(xMasFound2);
    }

    private int getxMasFound1(List<char[]> lines) {
        int found = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length; j++) {
                if (lines.get(i)[j] == 'X') {
                    for (Coord coord : ALL_COORDS) {
                        boolean isFound = true;
                        int currentI = i;
                        int currentJ = j;
                        for (char c : "MAS".toCharArray()) {
                            currentI += coord.x();
                            currentJ += coord.y();
                            if (currentI < 0 || currentI >= lines.size() || currentJ < 0 || currentJ >= lines.get(i).length || checkNext(lines, c, currentI, currentJ)) {
                                isFound = false;
                                break;
                            }
                        }
                        if (isFound) {
                            found++;
                        }
                    }
                }
            }
        }
        return found;
    }

    private boolean checkNext(List<char[]> lines, char c, int currentI, int currentJ) {
        return lines.get(currentI)[currentJ] != c;
    }

    private int getxMasFound2(List<char[]> lines) {
        int found = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length; j++) {
                if (lines.get(i)[j] == 'A') {
                    boolean isFound = true;

                    int mi = i + X_COORDS.get(0).x();
                    int mj = j + X_COORDS.get(0).y();
                    int si = i + X_COORDS.get(1).x();
                    int sj = j + X_COORDS.get(1).y();
                    isFound = checkMS(lines, isFound, mi, mj, si, sj);

                    mi = i + X_COORDS.get(2).x();
                    mj = j + X_COORDS.get(2).y();
                    si = i + X_COORDS.get(3).x();
                    sj = j + X_COORDS.get(3).y();
                    isFound = checkMS(lines, isFound, mi, mj, si, sj);

                    if (isFound) {
                        found++;
                    }
                }
            }
        }
        return found;
    }

    private boolean checkMS(List<char[]> lines, boolean isFound, int mi, int mj, int si, int sj) {
        if (!isValid(lines, mi, mj) || !isValid(lines, si, sj) ||
                !((lines.get(mi)[mj] == 'M' && lines.get(si)[sj] == 'S') || (lines.get(mi)[mj] == 'S' && lines.get(si)[sj] == 'M'))) {
            isFound = false;
        }
        return isFound;
    }

    private boolean isValid(List<char[]> lines, int i, int j) {
        return i >= 0 && i < lines.size() && j >= 0 && j < lines.get(i).length;
    }
}
