package aoc.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid<T> {
    private final Map<Coord, T> values;
    private final Map<Coord, Boolean> visited;
    private final T defaultValue;


    private Coord currentPos;

    public Grid(T defaultValue) {
        this.values = new HashMap<>();
        this.visited = new HashMap<>();
        this.defaultValue = defaultValue;
    }

    public T get(Coord coord) {
        return values.getOrDefault(coord, defaultValue);
    }

    public void set(Coord coord, T value) {
        values.put(coord, value);
    }

    public boolean isVisited(Coord coord) {
        return visited.getOrDefault(coord, false);
    }

    public Coord getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Coord currentPos) {
        this.currentPos = currentPos;
    }

    public void visit(Coord coord) {
        visited.put(coord, true);
    }

    public void clearVisited(Coord coord) {
        visited.put(coord, false);
    }

    public Coord move(Coord current, Direction direction) {
        return new Coord(current.x() + direction.dx(), current.y() + direction.dy());
    }

    public List<Coord> getCoordOfValue(T value) {
        List<Coord> coords = new ArrayList<>();
        for (Map.Entry<Coord, T> entry : values.entrySet()) {
            if (entry.getValue().equals(value)) {
                coords.add(entry.getKey());
            }
        }
        return coords;
    }

    public boolean contains(Coord coord) {
        return values.containsKey(coord);
    }


    public void print(int minX, int maxX, int minY, int maxY) {
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Coord coord = new Coord(x, y);
                if (contains(coord)) {
                    System.out.print(get(coord));
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
