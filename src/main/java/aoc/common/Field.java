package aoc.common;

import java.util.Objects;

public class Field<T> {

    private T value;
    private boolean visited;

    public Field(T value, boolean visited) {
        this.value = value;
        this.visited = visited;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field<?> field = (Field<?>) o;
        return visited == field.visited && Objects.equals(value, field.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, visited);
    }
}
