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
        Grid<Character> grid = Util.generateGrid(filePath);
        GuardPosition guardPosition = getGuardPosition(grid);

        if (guardPosition == null) {
            throw new IllegalArgumentException("Couldn't find initial Position!");
        }

        doInitialSetup(grid, guardPosition);

        simulateGuardMovement(grid, guardPosition, false);

        return String.valueOf(grid.getVisitedFields().size());
    }

    @Override
    public String solvePart2(String filePath) {
        Grid<Character> grid = Util.generateGrid(filePath);
        GuardPosition initialGuardPosition = getGuardPosition(grid);

        if (initialGuardPosition == null) {
            throw new IllegalArgumentException("Couldn't find initial Position!");
        }

        doInitialSetup(grid, initialGuardPosition);

        simulateGuardMovement(grid, initialGuardPosition, false);

        int numOfLoopPositions = 0;

        for (Coord coord : grid.getVisitedFields()) {
            if (coord.equals(initialGuardPosition.coordinates())) {
                continue;
            }

            Grid<Character> gridWithBlockage = Util.generateGrid(filePath);
            gridWithBlockage.set(initialGuardPosition.coordinates(), '.');
            gridWithBlockage.set(coord, '#');
            gridWithBlockage.setCurrentPos(initialGuardPosition.coordinates());

            boolean loopDetected = simulateGuardMovement(gridWithBlockage, initialGuardPosition, true);
            if (loopDetected) {
                numOfLoopPositions++;
            }
        }

        return String.valueOf(numOfLoopPositions);
    }

    private static void doInitialSetup(Grid<Character> grid, GuardPosition initialGuardPosition) {
        grid.set(initialGuardPosition.coordinates(), '.');
        grid.setCurrentPos(initialGuardPosition.coordinates());
        grid.visit(initialGuardPosition.coordinates);
    }

    private static boolean simulateGuardMovement(
            Grid<Character> grid,
            GuardPosition guardPosition,
            boolean detectLoop
    ) {
        Direction currentDirection = guardPosition.direction();

        Set<GuardPosition> visitedPositions = detectLoop ? new HashSet<>() : null;
        if (visitedPositions != null) {
            visitedPositions.add(guardPosition);
        }

        while (grid.isNextMoveLegal(currentDirection)) {
            Coord nextPos = grid.getNextCoord(currentDirection);
            char nextCell = grid.get(nextPos);

            if (nextCell == '.') {
                grid.move(currentDirection);
                if (detectLoop) {
                    GuardPosition currentGuardPos = new GuardPosition(grid.getCurrentPos(), currentDirection);
                    if (!visitedPositions.add(currentGuardPos)) {
                        return true;
                    }
                }
            } else if (nextCell == '#') {
                currentDirection = currentDirection.turnRight();
            } else {
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
