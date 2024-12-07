package aoc;

import aoc.common.Day;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[94m";

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.print(GREEN);
        System.out.println("       🌟        ");
        System.out.println("       ***       ");
        System.out.println("      *****      ");
        System.out.println("     *******     ");
        System.out.println("    *********    ");
        System.out.println("       ***       ");
        System.out.println("       ***       ");
        System.out.println();
        System.out.println(GREEN + "Welcome to Feli's Advent of Code Solution!");
        System.out.println("🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟");
        System.out.println();
        mainMenu();
    }

    private static void mainMenu() {
        boolean running = true;
        String year = String.valueOf(LocalDate.now().getYear());
        boolean testMode = false;

        while (running) {
            System.out.println(RESET + "What do you want to do?");
            System.out.println(RED + "1" + GREEN + ": Solve Daily Puzzle for the year " + year);
            System.out.println(RED + "2" + GREEN + ": Change Years (Current: " + year + ")");
            System.out.println(RED + "3" + GREEN + ": Toggle Test-Mode (Current: " + (testMode ? "Enabled" : "Disabled") + ")");
            System.out.println(RED + "4" + GREEN + ": Access Developer Tools");
            System.out.println(RED + "5" + GREEN + ": Quit");
            System.out.println(RESET);
            System.out.print("Select an option: " + RED);
            String choice = scanner.nextLine().trim();
            System.out.print(RESET);

            switch (choice) {
                case "1":
                    mainLoop(year, testMode);
                    break;
                case "2":
                    year = setYear();
                    break;
                case "3":
                    testMode = !testMode;
                    System.out.println(GREEN + "Test-Mode is now " + (testMode ? "Enabled" : "Disabled") + "." + RESET);
                    break;
                case "4":
                    devMode();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println(RED + "Invalid option. Try again." + RESET);
            }
        }
        System.out.println(GREEN + "Goodbye!" + RESET);
    }

    private static void devMode() {
        System.out.println(GREEN + "Dev-Mode activated! 🎄" + RESET);
        boolean running = true;

        while (running) {
            System.out.println(GREEN + "Developer Tools:" + RESET);
            System.out.println(RED + "1: Fetch new puzzle input" + RESET);
            System.out.println(RED + "2: Return to Main Menu" + RESET);
            System.out.print(GREEN + "Select an option: " + RESET + BLUE);
            String choice = scanner.nextLine().trim();
            System.out.print(RESET);

            switch (choice) {
                case "1":
                    fetchPuzzleInput();
                    break;
                case "2":
                    running = false;
                    System.out.println(GREEN + "Returning to Main Menu..." + RESET);
                    break;
                default:
                    System.out.println(RED + "Invalid option. Try again." + RESET);
            }
        }
    }

    private static void fetchPuzzleInput() {
        System.out.println(GREEN + "Fetching new puzzle input... (Feature coming soon!)" + RESET);
    }

    private static void mainLoop(String year, boolean testMode) {
        boolean inLoop = true;

        while (inLoop) {
            System.out.println();
            System.out.println("🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟");
            System.out.print(GREEN + "Enter a day " + RED + "(##)" + GREEN + " to solve, " + RED + "m" + GREEN + " for menu, or " + RED + "q" + GREEN + " to quit: " + RED);
            String input = scanner.nextLine().trim().toLowerCase();
            System.out.print(RESET);

            switch (input) {
                case "m":
                    inLoop = false;
                    break;
                case "q":
                    System.out.println(GREEN + "Goodbye!" + RESET);
                    System.exit(0);
                    break;
                default:
                    if (isDayInputValid(input)) {
                        executeDay(year, testMode, input);
                    } else {
                        System.out.println(RED + "Invalid input. Try again!" + RESET);
                    }
            }
        }
    }

    private static String setYear() {
        boolean validYear = false;
        String year = "";

        while (!validYear) {
            System.out.print(GREEN + "Enter year " + RED + "(####): " + RED);
            year = scanner.nextLine().trim();

            if (isYearValid(year)) {
                validYear = true;
            } else {
                System.out.println(RED + "Invalid year or year does not exist. Please try again." + RESET);
            }
        }

        System.out.println(GREEN + "Year set to " + year + "." + RESET);
        return year;
    }

    private static boolean isYearValid(String year) {
        if (!year.matches("\\d{4}")) {
            return false;
        }

        String yearPath = "input/year" + year;
        return new java.io.File(yearPath).exists();
    }


    private static boolean isDayInputValid(String input) {
        try {
            int day = Integer.parseInt(input);
            return day >= 1 && day <= 25;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void executeDay(String year, boolean testMode, String day) {
        String filePath = constructFilePath(year, day, testMode);
        try {
            String className = constructClassName(year, day);
            Day dayInstance = createDayInstance(className);
            System.out.println(RESET);
            System.out.println("Solutions for day " + day + ":");
            System.out.println(GREEN + "Part 1: " + RESET + dayInstance.solvePart1(filePath));
            System.out.println(GREEN + "Part 2: " + RESET + dayInstance.solvePart2(filePath));
        } catch (ClassNotFoundException e) {
            System.err.println(RED + "Day " + day + " of year " + year + " doesn't exist yet. Please implement the class or select another day." + RESET);
        } catch (java.nio.file.NoSuchFileException e) {
            System.err.println(RED + "Input file for Day " + day + " (" + filePath + ") does not exist. Please check the file or switch to a different day." + RESET);
        } catch (IllegalStateException e) {
            System.err.println(RED + "Input file for Day " + day + " is missing or unreadable. Details: " + e.getCause().getMessage() + RESET);
        } catch (Exception e) {
            System.err.println(RED + "Ouuups! Something went wrong while executing Day " + day + ". Here's what happened:" + RESET);
            e.printStackTrace();
        }
    }

    private static String constructFilePath(String year, String day, boolean testMode) {
        String mode = testMode ? "test" : "real";
        return "input/year" + year + "/" + mode + "/Day" + day + ".txt";
    }

    private static String constructClassName(String year, String day) {
        return "aoc.year" + year + ".Day" + day;
    }

    private static Day createDayInstance(String className) throws Exception {
        Class<?> dayClass = Class.forName(className);
        return (Day) dayClass.getDeclaredConstructor().newInstance();
    }
}
