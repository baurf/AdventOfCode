package aoc.year2024;

import aoc.common.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day06 implements Day {

    private static final Map<Character, Direction> DIRECTION_MAP = Map.of(
            '^', Direction.UP,
            '>', Direction.RIGHT,
            'v', Direction.DOWN,
            '<', Direction.LEFT
    );

    @Override
    public String solvePart1(String filePath) {
        List<String> lines = Util.getResourceAsStringList(filePath);
        Grid<Character> grid = Util.generateGrid(lines, '.');

        Coord initialPos = null;
        Direction initialDirection = null;

        for (char dirChar : DIRECTION_MAP.keySet()) {
            List<Coord> matches = grid.getCoordOfValue(dirChar);
            if (!matches.isEmpty()) {
                initialPos = matches.get(0); // Assume only one starting position
                initialDirection = DIRECTION_MAP.get(dirChar);
                break;
            }
        }

        if (initialPos == null || initialDirection == null) {
            throw new RuntimeException("Couldn't find initial Position!");
        }


        Coord currentPos = initialPos;
        Direction currentDirection = initialDirection;
        Set<Coord> visited = new HashSet<>();
        visited.add(currentPos);

        while (true) {
            Coord nextPos = currentDirection.moveStraight(currentPos);
            char nextCell = grid.get(nextPos);

            if (nextCell == '.') {
                currentPos = nextPos;
                visited.add(currentPos);
            } else if (nextCell == '#') {
                currentDirection = currentDirection.turnRight();
            } else {
                break;
            }

            if (!grid.contains(nextPos)) {
                break;
            }
        }

        return String.valueOf(visited.size());
    }

    @Override
    public String solvePart2(String filePath) {
        List<String> lines = Util.getResourceAsStringList(filePath);

        char[][] grid = Util.parseToCharGrid(filePath);

        int guardRow = 0, guardCol = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if ("^v<>".indexOf(grid[r][c]) != -1) {
                    guardRow = r;
                    guardCol = c;
                }
            }
        }

        Set<String> validPositions = new HashSet<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == '.' && !(r == guardRow && c == guardCol)) {
                    grid[r][c] = '#';

                    if (isLoopingPath(grid)) {
                        validPositions.add(r + "," + c);
                    }

                    grid[r][c] = '.';
                }
            }
        }

        return String.valueOf(validPositions.size());
    }

    private boolean isLoopingPath(char[][] grid) {
        // Directions: up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        char[] directionChars = {'^', '>', 'v', '<'};

        // Find the guard's initial position and direction
        int guardRow = 0, guardCol = 0, directionIndex = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                for (int d = 0; d < directionChars.length; d++) {
                    if (grid[r][c] == directionChars[d]) {
                        guardRow = r;
                        guardCol = c;
                        directionIndex = d;
                        break;
                    }
                }
            }
        }

        Set<String> visitedStates = new HashSet<>();
        String initialState = guardRow + "," + guardCol + "," + directionIndex;
        visitedStates.add(initialState);

        while (true) {
            int nextRow = guardRow + directions[directionIndex][0];
            int nextCol = guardCol + directions[directionIndex][1];

            if (nextRow < 0 || nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length) {
                return false; // No loop; the guard exits the map
            }

            if (grid[nextRow][nextCol] == '#') {
                directionIndex = (directionIndex + 1) % 4;
            } else {
                guardRow = nextRow;
                guardCol = nextCol;
            }

            String state = guardRow + "," + guardCol + "," + directionIndex;
            if (visitedStates.contains(state)) {
                return true;
            }
            visitedStates.add(state);
        }
    }
}
