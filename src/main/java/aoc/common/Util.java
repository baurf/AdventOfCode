package aoc.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static List<List<String>> getResourceAsStringList(String resource, int amount, String regex) {
        try {
            List<List<String>> columns = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                columns.add(new ArrayList<>());
            }

            Files.readAllLines(getResource(resource).toPath()).forEach(line -> {
                String[] values = line.split(regex);
                for (int i = 0; i < amount; i++) {
                    columns.get(i).add(values[i]);
                }
            });

            return columns;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<String> getResourceAsStringList(String resource) {
        try {
            return Files.readAllLines(getResource(resource).toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getResourceAsString(String resource) {
        try {
            return Files.readString(getResource(resource).toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static File getResource(String path) {
        return new File("src/main/resources/" + path);
    }

    public static List<List<Integer>> splitAndMapToInt(List<String> strings, String regex) {
        return strings.stream()
                .map(it -> Arrays.stream(it.split(regex))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public static List<List<Long>> splitAndMapToLong(List<String> strings, String regex) {
        return strings.stream()
                .map(it -> Arrays.stream(it.split(regex))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public static Grid<Character> generateGrid(String resource) {
        List<String> lines = getResourceAsStringList(resource);
        Grid<Character> grid = new Grid<>();

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                char cell = lines.get(y).charAt(x);
                grid.set(new Coord(x, y), cell);
            }
        }

        return grid;
    }
}
