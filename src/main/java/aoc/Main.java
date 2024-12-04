package aoc;

import java.util.Scanner;

import aoc.common.Day;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    String yearInput = getUserInput(scanner, "Enter year (####): ");
    String dayInput = getUserInput(scanner, "Enter day (##): ");
    String partInput = getUserInput(scanner, "Enter part (1, 2, or leave blank for both): ");

    String filePath = constructFilePath(yearInput, dayInput);
    executeDaySolution(yearInput, dayInput, partInput, filePath);

    scanner.close();
  }

  private static String getUserInput(Scanner scanner, String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  private static String constructFilePath(String year, String day) {
    return "input/year" + year + "/real/Day" + day + ".txt";
  }
  private static void executeDaySolution(String year, String day, String part, String filePath) {
    try {
      String className = constructClassName(year, day);
      Day dayInstance = createDayInstance(className);

      if ("1".equals(part)) {
        printPartSolution(part, dayInstance.solvePart1(filePath));
      } else if ("2".equals(part)) {
        printPartSolution(part, dayInstance.solvePart2(filePath));
      } else {
        printPartSolution("1", dayInstance.solvePart1(filePath));
        printPartSolution("2", dayInstance.solvePart2(filePath));
      }

    } catch (ClassNotFoundException e) {
      System.err.println("Class not found: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error creating instance: " + e.getMessage());
    }
  }

  private static String constructClassName(String year, String day) {
    return "aoc.year" + year + ".Day" + day;
  }

  private static Day createDayInstance(String className) throws Exception {
    Class<?> dayClass = Class.forName(className);
    return (Day)dayClass.getDeclaredConstructor().newInstance();
  }

  private static void printPartSolution(String part, String result) {
    System.out.println("Part " + part + " Solution: " + result);
    System.out.println();
  }
}