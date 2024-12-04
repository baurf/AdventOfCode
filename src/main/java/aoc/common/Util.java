package aoc.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
}
