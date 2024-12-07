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
        Grid<Character> grid = Util.generateGrid(filePath, '.');
        GuardPosition guardPosition = getGuardPosition(grid);

        if (guardPosition == null) {
            throw new RuntimeException("Couldn't find initial Position!");
        }

        grid.set(guardPosition.coordinates(), '.');

        Set<Coord> visitedCoords = new HashSet<>();
        simulateGuardMovementForPart1(guardPosition, grid, visitedCoords);

        for (Coord coord : visitedCoords) {
            grid.set(coord, 'X');
        }
        return String.valueOf(visitedCoords.size());
    }

    @Override
    public String solvePart2(String filePath) {
        Grid<Character> grid = Util.generateGrid(filePath, '.');
        GuardPosition initialGuardPosition = getGuardPosition(grid);

        if (initialGuardPosition == null) {
            throw new RuntimeException("Couldn't find initial Position!");
        }

        grid.set(initialGuardPosition.coordinates(), '.');

        Set<Coord> visitedCoords = new HashSet<>();
        simulateGuardMovementForPart1(initialGuardPosition, grid, visitedCoords);

        int numOfLoopPositions = 0;

        for (Coord coord : visitedCoords) {
            if (coord.equals(initialGuardPosition.coordinates())) {
                continue; // Skip the starting position
            }

            Grid<Character> gridWithBlockage = Util.generateGrid(filePath, '.');
            gridWithBlockage.set(initialGuardPosition.coordinates(), '.');
            gridWithBlockage.set(coord, '#');

            Set<GuardPosition> testVisited = new HashSet<>();
            boolean loopDetected = simulateGuardMovementForPart2(initialGuardPosition, gridWithBlockage, testVisited);

            if (loopDetected) {
                numOfLoopPositions++;
            }
        }

        return String.valueOf(numOfLoopPositions);
    }

    private static void simulateGuardMovementForPart1(
            GuardPosition guardPosition,
            Grid<Character> grid,
            Set<Coord> visitedCoords
    ) {
        Coord currentPos = guardPosition.coordinates();
        Direction currentDirection = guardPosition.direction();

        visitedCoords.add(currentPos);

        while (true) {
            Coord nextPos = currentDirection.moveStraight(currentPos);

            if (!grid.contains(nextPos)) {
                break;
            }

            char nextCell = grid.get(nextPos);

            if (nextCell == '.') {
                currentPos = nextPos;
                visitedCoords.add(currentPos);
            } else if (nextCell == '#') {
                currentDirection = currentDirection.turnRight();
            } else {
                break;
            }
        }
    }

    private static boolean simulateGuardMovementForPart2(
            GuardPosition guardPosition,
            Grid<Character> grid,
            Set<GuardPosition> visited
    ) {
        Coord currentPos = guardPosition.coordinates();
        Direction currentDirection = guardPosition.direction();

        visited.add(guardPosition);

        while (true) {
            Coord nextPos = currentDirection.moveStraight(currentPos);
            char nextCell = grid.get(nextPos);

            if (nextCell == '.') {
                currentPos = nextPos;
                GuardPosition currentGuardPos = new GuardPosition(currentPos, currentDirection);
                if (!visited.add(currentGuardPos)) {
                    return true;
                }
            } else if (nextCell == '#') {
                currentDirection = currentDirection.turnRight();
            } else {
                break;
            }

            if (!grid.contains(nextPos)) {
                break;
            }
        }

        return false;
    }

    private static GuardPosition getGuardPosition(Grid<Character> grid) {
        for (char dirChar : DIRECTION_MAP.keySet()) {
            List<Coord> matches = grid.getCoordOfValue(dirChar);
            if (!matches.isEmpty()) {
                return new GuardPosition(matches.get(0), DIRECTION_MAP.get(dirChar));
            }
        }
        return null;
    }

    private record GuardPosition(Coord coordinates, Direction direction) {
    }
}
