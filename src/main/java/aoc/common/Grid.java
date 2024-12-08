package aoc.common;

import java.util.*;

public class Grid<T> {
    private final Map<Coord, Field<T>> map = new HashMap<>();
    private Coord currentPos;

    public Grid() {
    }

    public T get(Coord coord) {
        Field<T> field = map.get(coord);
        return (field != null) ? field.getValue() : null;
    }

    public void set(Coord coord, T value) {
        map.put(coord, new Field<>(value, isVisited(coord)));
    }

    public void setCurrentPos(Coord currentPos) {
        this.currentPos = currentPos;
    }

    public boolean isVisited(Coord coord) {
        Field<T> field = map.get(coord);
        return (field != null) && field.isVisited();
    }

    public void visit(Coord coord) {
        Field<T> field = map.get(coord);
        if (field != null) {
            field.setVisited(true);
        } else {
            throw new IllegalArgumentException("Das Feld gits ned imfall");
        }

    }

    public void move(Direction direction) {
        Coord nextPos = new Coord(currentPos.x() + direction.dx(), currentPos.y() + direction.dy());
        visit(nextPos);
        currentPos = nextPos;
    }

    public List<Coord> getVisitedFields() {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue().isVisited())
                .map(Map.Entry::getKey)
                .toList();
    }

    public Coord getNextCoord(Direction direction) {
        return new Coord(currentPos.x() + direction.dx(), currentPos.y() + direction.dy());
    }

    public boolean isNextMoveLegal(Direction direction) {
        return map.containsKey(getNextCoord(direction));
    }

    public List<Coord> getCoordOfValue(T value) {
        List<Coord> coords = new ArrayList<>();
        for (Map.Entry<Coord, Field<T>> entry : map.entrySet()) {
            if (entry.getValue().getValue().equals(value)) {
                coords.add(entry.getKey());
            }
        }
        return coords;
    }

    public boolean contains(Coord coord) {
        return map.containsKey(coord);
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

    public Coord getCurrentPos() {
        return this.currentPos;
    }

    public Set<Map.Entry<Coord, Field<T>>> entrySet() {
        return map.entrySet();
    }
}
